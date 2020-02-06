package team;

import matches.MatchOutcome;
import personnel.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to create a new team
 */
public class Team
{
    private static final int STARTING_MONEY = 0;

    private List<Player> playerList;
    private String name;
    private boolean notPlayer;
    private int offensiveSkill;
    private int defensiveSkill;
    private int points;
    private Map<MatchOutcome,Integer> outcomes;
    private int money;
    private Player starterGoalie;
    private List<Coach> coachList;
    private List<Personnel> personnelList;

    public Team(final List<Player> playerList, final String name, boolean notPlayer) {
        money = STARTING_MONEY;
        this.playerList = playerList;
        this.name = name;
        this.notPlayer = notPlayer;
        this.points = 0;
        this.outcomes = new EnumMap<>(MatchOutcome.class);
        initOutcomeMap();
        setJointOffensiveSkill();
        setJointDefensiveSkill();
        this.starterGoalie = playerList.get(0);//Set first player in troop as temporary starterGoalie
        this.starterGoalie = getBestGoalie();

        this.coachList = new ArrayList<>();
        this.personnelList = new ArrayList<>(playerList);

    }

    public void addMoney(int money){
        this.money += money;
    }

    public void initOutcomeMap(){
        for (MatchOutcome outcome: MatchOutcome.values()) {
            outcomes.put(outcome, Integer.valueOf(0));
        }
    }

    /**
     * @return the goalie with highest skill in team
     */
    public Player getBestGoalie(){
        Player goalie = playerList.get(0);
        for (Player player : playerList) {
            if(player.getRole() == PlayerRole.GOALKEEPER && player.getGoalieSkill() > goalie.getGoalieSkill()){
                goalie = player;
            }
        }
        return goalie;
    }

    public void payPersonnel(){
        for (Personnel personnel: personnelList) {
            money -= personnel.getSalary();
            if(money < 0){
                money = 0;
                return;
            }
        }
    }

    public boolean hasMaxOfCoachType(CoachType coachType){
        int coaches = 0;
        for (Coach coach: coachList) {
            if (coach.getCoachType() == coachType){
                coaches++;
            }
        }
        return coaches==coachType.getMaxAmount();
    }

    public void addCoach(Coach coach){
        if (!hasMaxOfCoachType(coach.getCoachType())) {
            money -= coach.getCoachType().getPrice();
            coachList.add(coach);
            personnelList.add(coach);
            offensiveSkill += coach.getOffensiveBoost();
            defensiveSkill += coach.getDefensiveBoost();
            starterGoalie.setGoalieSkill(starterGoalie.getGoalieSkill()+coach.getGoalieBoost());
        }
    }

    public int getMoney() {
        return money;
    }

    public void setStarterGoalie(Player player){
        starterGoalie = player;
    }

    public Player getStarterGoalie() {
        return starterGoalie;
    }

    public void addOutcome(MatchOutcome outcome){
        points += outcome.getPointReward();
        money += outcome.getMoneyReward();
        outcomes.put(outcome, Integer.valueOf(outcomes.get(outcome) + 1));
    }

    public Map<MatchOutcome, Integer> getOutcomes() {
        return outcomes;
    }

    public int getPoints() {
        return points;
    }


    public void setJointOffensiveSkill(){
        int totalSkillLevel = 0;
        for (Player player: playerList) {
            totalSkillLevel += player.getOffensiveSkill();
        }
        this.offensiveSkill = (int)Math.round((double)totalSkillLevel / playerList.size());
    }

    public void setJointDefensiveSkill(){
        int totalSkillLevel = 0;
        for (Player player: playerList) {
            totalSkillLevel += player.getDeffensiveSkill();
        }
        this.defensiveSkill = (int)Math.round((double)totalSkillLevel / playerList.size());
    }

    public void updateAges(LocalDate currentDate){
        for (Player player: playerList) {
            int age = currentDate.getYear() - player.getBirthday().getYear();
            if(currentDate.getMonth().compareTo(player.getBirthday().getMonth()) < 0 ||
                (currentDate.getMonth().compareTo(player.getBirthday().getMonth())==0 &&
                player.getBirthday().getDayOfMonth() > currentDate.getDayOfMonth())){
                age--;
            }
            player.setAge(age);
        }
    }

    public void clearResults(){
        points = 0;
        initOutcomeMap();
    }

    public int getOffensiveSkill() {
        return offensiveSkill;
    }

    public int getDefensiveSkill() {
        return defensiveSkill;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public boolean isNotPlayer() {
        return notPlayer;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return name;
    }
}
