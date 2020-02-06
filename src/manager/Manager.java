package manager;

import matches.Match;
import seasons.LeagueSeason;
import seasons.PlayoffSeason;
import seasons.Season;
import team.Team;
import team.TeamCreator;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create a new player in the game.
 * Contains current game info
 * A new manager.Manager is created when creating a new game
 */
public class Manager {


    /**
     * Start date of a new game
     */
    public static final LocalDate START_DATE = LocalDate.of(2018, Month.SEPTEMBER, 1);

    private static final int LEAGUE_SIZE = 10;

    private Team team;
    private String managerName;
    private Match currentMatch;
    private LocalDate currentDate;
    private Season season;
    private List<ManagerListener> managerListeners;
    private String latestResult;


    public Manager(String managerName, String teamName) {
        this.currentMatch = null;
        this.managerName = managerName;
        currentDate = START_DATE;
        List<Team> teamList = new ArrayList<>();
        team = TeamCreator.createRandomTeam(false, teamName);
        team.updateAges(currentDate);
        teamList.add(team); //Add the player controlled team
        teamList.addAll(TeamCreator.createRandomTeams(LEAGUE_SIZE-1)); //Add the non-player teams
        season = new LeagueSeason(START_DATE, teamList);//Create a new season with the generated teams
        managerListeners = new ArrayList<>();
        latestResult = "";
    }

    public void addManagerListener(ManagerListener listener){
        managerListeners.add(listener);
    }

    public void notifyListeners(){
        for (ManagerListener listener: managerListeners) {
            listener.statusChanged();
        }
    }

    public void simulateTodaysMatches(){
        season.simulateMatches(currentDate);
        updateLatestResult();
        notifyListeners();
    }

    public Season getSeason() {
        return season;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void updateLatestResult(){
        if(currentMatch != null && currentMatch.hasEnded()){
            latestResult = currentMatch.getScore();
        }
    }
    public void updateCurrentMatch(){
        currentMatch = season.getMatchByDate(team, currentDate);
    }

    public Match getCurrentMatch() {
        return currentMatch;
    }

    public Team getTeam() {
        return team;

    }


    public String getManagerName(){
        return managerName;
    }

    public String getLatestResult() {
        return latestResult;
    }

    /**
     * Sets the current season to a season that does not have the same type as the previous season
     */
    public void newSeason(){
        if(season.getClass().equals(LeagueSeason.class)){
            season = new PlayoffSeason(currentDate, season.getTopEight());
        }
        else{
            List<Team> teamList = TeamCreator.createRandomTeams(LEAGUE_SIZE-1);
            teamList.add(0,team);//Add current team to teamlist
            season = new LeagueSeason(currentDate,teamList);
        }
    }


    public void advanceDate() {
        season.simulateMatches(currentDate);//Pay salary at start of month
        team.payPersonnel();

        if(currentDate.isAfter(season.getEndDate())){//If season has ended start new season
            newSeason();
        }

        team.updateAges(currentDate);
        this.currentDate = currentDate.plusDays(1);
        updateCurrentMatch();
        notifyListeners();

    }
}
