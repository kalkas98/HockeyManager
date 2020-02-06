package seasons;


import team.Team;

import java.time.LocalDate;
import java.util.List;

/**
 * Classs used to create a regular season with league matches
 */
public class LeagueSeason extends AbstractSeason {


    private static final int MATCH_MULTIPLIER = 2;//Number of times a team has to face every other team
    private static final int MATCH_SPACE = 4;//Preffered space betweeen matches

    public LeagueSeason(LocalDate startDate, List<Team> teamList) {
        super(teamList);
        generateMatches(startDate);
    }

    public void generateMatches(LocalDate intervalStart){

        List<Team> teams = getTeamList();
        LocalDate endDate = intervalStart;
        for (int i = 0; i < teams.size()* MATCH_MULTIPLIER; i++) {
            LocalDate matchDate = LocalDate.of(intervalStart.getYear(),intervalStart.getMonth(),intervalStart.getDayOfMonth());
            matchDate = matchDate.plusDays(MATCH_SPACE *(new Long(i) +1));
            for (int j = 1+(i)%teams.size(); j < teams.size(); j++) {
                while(hasMatch(teams.get(i%teams.size()),matchDate) || hasMatch(teams.get(j),matchDate)){
                    //If date is already occupied  for any of the teams, try the next day
                    matchDate = matchDate.plusDays(1);
                }
                addMatch(matchDate,teams.get(i%teams.size()),teams.get(j));
                matchDate = matchDate.plusDays(4);
            }
            if (endDate.isBefore(matchDate)){
                //endDate will be set to date of last match in season
                endDate = matchDate;
            }
        }
        setEndDate(endDate);
    }

    @Override
    public String toString() {
        return "Regular season";
    }
}
