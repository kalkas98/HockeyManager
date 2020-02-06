package matches;

import team.Team;

import java.time.LocalDate;

/**
 * Match interface to be implemented by all Match classes
 */
public interface Match {
    public void simulateMatch();
    public Team getWinner();
    public MatchOutcome getPlayerOutcome();
    public String getScore();
    public Team getTeam2();
    public Team getTeam1();
    public LocalDate getMatchDate();
    public MatchOutcome getTeam1Outcome();
    public boolean hasEnded();

}
