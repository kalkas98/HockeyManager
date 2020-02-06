package seasons;

import manager.Manager;
import matches.LeagueMatch;
import matches.Match;
import matches.MatchOutcome;
import team.Team;
import team.TeamScoreComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class specifying functionality and properties of season classes
 */
public abstract class AbstractSeason implements Season{

    private List<Team> teamList;
    private List<Match> matchList;
    private LocalDate endDate;

    protected AbstractSeason(List<Team> teamList) {
        this.teamList = new ArrayList<>(teamList);
        this.matchList = new ArrayList<>();
        this.endDate = Manager.START_DATE;/*Initialized to default value here, later given "real" value
                                          when Season.generateMatches() is called
                                          */
    }

    public List<Team> getTeamList() {
        return teamList;
    }
    public List<Match> getMatchList() {
        return matchList;
    }

    public void sortTeamList(){//Sort teamlist, team with most points first in list
        teamList.sort(new TeamScoreComparator());
        Collections.reverse(teamList);
    }

    public void simulateMatches(LocalDate date){
        for (Match match: matchList) {//Simluatets all matches at date
            if(match.getTeam1Outcome() == MatchOutcome.NOT_PLAYED && match.getMatchDate().equals(date)){
                match.simulateMatch();
            }
        }
        sortTeamList();
    }

    @Override
    public void resetTeamStandings(){
        for (Team team: teamList) {
            team.clearResults();
        }
    }

    public void addMatch(LocalDate date, Team team1, Team team2){
        matchList.add(new LeagueMatch(team1, team2, date));
    }

    public boolean hasMatch(Team team, LocalDate date){
        return getMatchByDate(team, date) != null;//Returns true if team has a match at a specific date in this season
    }

    public boolean hasAnyMatch(LocalDate date){
        for (Team team : teamList) {//Return true if any team as a match at date
            if (hasMatch(team,date) && getMatchByDate(team,date).getTeam1Outcome() == MatchOutcome.NOT_PLAYED ){
                return true;
            }
        }
        return false;
    }

    public Match getMatchByDate(Team team, LocalDate date){
        for (Match match: matchList) {
            if(match.getMatchDate().equals(date) && (team.equals(match.getTeam1()) || team.equals(match.getTeam2()))){
                return match;
            }
        }
        return null;//If there is not match at the date for the team given, return null
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Team> getTopEight(){//Returns the top eight teams with the most points in the season
        List<Team> topEight = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            topEight.add(teamList.get(i));
        }
        return topEight;
    }

}
