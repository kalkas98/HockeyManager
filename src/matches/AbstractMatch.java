package matches;

import team.Team;

import java.time.LocalDate;
import java.util.Random;

/**
 * Abstract class containing functionality for matches
 */
public abstract class AbstractMatch implements Match {

    private Team team1;
    private Team team2;
    private Random random;
    private int team1Score, team2Score;
    private MatchOutcome team1Outcome, team2Outcome;
    private LocalDate matchDate;

    protected AbstractMatch(final Team team1, final Team team2, final LocalDate date) {
        this.matchDate = date;
        this.team1 = team1;
        this.team2 = team2;
        this.team1Outcome = MatchOutcome.NOT_PLAYED;
        this.team2Outcome = MatchOutcome.NOT_PLAYED;
        team1Score = 0;
        team2Score = 0;
        random = new Random();
    }


    public void defaultMatchSimulator() {
        for (int i = 0; i < 3; i++) {
            simulatePeriod();
        }
        setOutcome();
    }


    public void simulatePeriod() {

        double team1OffSkill = (double)team1.getOffensiveSkill()/100;
        double team2OffSkill = (double)team2.getOffensiveSkill()/100;

        double team1DefSkill = (double)team1.getDefensiveSkill()/100;
        double team2DefSkill = (double)team2.getDefensiveSkill()/100;

        double team1GoalieSkill = (double)team1.getStarterGoalie().getGoalieSkill()/100;
        double team2GoalieSkill = (double)team2.getStarterGoalie().getGoalieSkill()/100;

        for (int i = 0; i < 2; i++) {//Loop giving both teams chance to score multiple times during a period
            team1Score += generateGoals(team1OffSkill,team2DefSkill,team2GoalieSkill,2);
            team2Score += generateGoals(team2OffSkill,team1DefSkill,team1GoalieSkill,2);
        }
    }

    public int generateGoals(double offSkills, double defSkills, double goalieSkills, int maxGoals){
        int goals = (int)Math.round(offSkills + Math.random() - (goalieSkills + defSkills - offSkills)) * random.nextInt(maxGoals+1);
        return goals;
    }

    public void setOutcome() {
        if(team1Score > team2Score){
            team1Outcome = MatchOutcome.WIN;
            team2Outcome = MatchOutcome.LOSS;
        }
        else if(team2Score > team1Score){
            team2Outcome = MatchOutcome.WIN;
            team1Outcome = MatchOutcome.LOSS;
        }
        else{
            team1Outcome = MatchOutcome.TIE;
            team2Outcome = MatchOutcome.TIE;
        }
    }

    @Override
    public Team getWinner() {
        if (team1Outcome == MatchOutcome.WIN){
            return team1;
        }
        else if(team2Outcome == MatchOutcome.WIN){
            return team2;
        }
        return null;
    }

    @Override
    public MatchOutcome getPlayerOutcome() {
        if (!team1.isNotPlayer()){
            return team1Outcome;
        }
        else if(!team2.isNotPlayer()){
            return team2Outcome;
        }
        else{
            return null;
        }
    }


    @Override
    public boolean hasEnded(){
        return (team1Outcome != MatchOutcome.NOT_PLAYED);
    }
    @Override
    public String getScore() {
        return team1.getName()+ " "+team1Score + " - " + team2Score +" "+ team2.getName();
    }

    @Override
    public String toString() {
        return String.format("%s - %s", team1.getName(), team2.getName());
    }

    @Override
    public Team getTeam2() {
        return team2;
    }
    @Override
    public Team getTeam1() {
        return team1;
    }

    @Override
    public LocalDate getMatchDate() {
        return matchDate;
    }

    @Override
    public MatchOutcome getTeam1Outcome() {
        return team1Outcome;
    }

    public MatchOutcome getTeam2Outcome() {
        return team2Outcome;
    }
}
