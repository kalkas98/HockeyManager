package matches;

import team.Team;

import java.time.LocalDate;

/**
 * Class used to create and simulate matches for the LeagueSeason class
 */
public class LeagueMatch extends AbstractMatch
{


	public LeagueMatch(Team team1, Team team2, LocalDate date) {
		super(team1, team2, date);
	}

	public void simulateMatch(){
		defaultMatchSimulator();

		getTeam1().addOutcome(getTeam1Outcome());
		getTeam2().addOutcome(getTeam2Outcome());

	}
}
