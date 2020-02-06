package seasons;

import matches.Match;
import team.Team;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface to be implemented by season classes that contain matches
 */
public interface Season {

    public Match getMatchByDate(Team team, LocalDate date);
    public boolean hasMatch(Team team, LocalDate date);
    public void simulateMatches(LocalDate date);
    public List<Team> getTeamList();
    public LocalDate getEndDate();
    public List<Team> getTopEight();
    public void resetTeamStandings();
}
