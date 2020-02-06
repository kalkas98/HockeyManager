package matches;

import seasons.PlayoffRound;
import team.Team;

import java.time.LocalDate;

/**
 * Class used to create playoff matches for the PlayOff Season
 */
public class PlayoffMatch extends AbstractMatch {
    private PlayoffRound playoffRound;
    public PlayoffMatch(Team team1, Team team2, LocalDate date, PlayoffRound round) {
        super(team1, team2, date);
        playoffRound = round;
    }

    public void simulateMatch(){
        defaultMatchSimulator();
        while(getTeam1Outcome() == MatchOutcome.TIE){//Can't be a tie in playoff elimination, simulate until someone wins
            simulatePeriod();
            setOutcome();
        }
        getWinner().addMoney(playoffRound.getMoneyReward());
    }
}
