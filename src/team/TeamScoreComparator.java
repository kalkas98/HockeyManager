package team;

import java.util.Comparator;

/**
 * Comparator that takes two teams and returns the points of the team with the most points
 */
public class TeamScoreComparator implements Comparator<Team> {
    @Override public int compare(final Team team1, final Team team2){
        return Integer.compare(team1.getPoints(),team2.getPoints());
    }
}
