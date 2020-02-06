package seasons;


import matches.PlayoffMatch;
import team.Team;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class speciying structure of the playoff season in the game, used to generate and simulate matches during the playoff
 */
public class PlayoffSeason extends AbstractSeason {

    private static final int ROUND_GAP = 4;//Gap between playoff matches
    private static final int NUMBER_OF_ROUNDS = 3;//NUMBER OF PLAYOFFROUNDS
    private PlayoffRound currentRound;

    public PlayoffSeason(LocalDate startDate, List<Team> teamList) {
        super(teamList);
        currentRound = PlayoffRound.QUARTERFINAL;
        setEndDate(startDate.plusDays(ROUND_GAP*(NUMBER_OF_ROUNDS+1)));
        initPlayoff(startDate);

    }

    public void initPlayoff(LocalDate date){
        List<Team> teams = getTeamList();
        Collections.reverse(teams); //Team at index 0 will be worst team to make it to the playoffs
        for (int i = 0; i < 4; i++) {//Add quarterfinal matches to the palyoffs
            addMatch(date.plusDays(ROUND_GAP),teams.get(i),teams.get(teams.size()-(i+1)),PlayoffRound.QUARTERFINAL);
        }
    }

    public void addMatch(LocalDate date, Team team1, Team team2,PlayoffRound playoffRound){
        getMatchList().add(new PlayoffMatch(team1, team2, date, playoffRound));
    }

    @Override
    public void simulateMatches(LocalDate date){
        if(!hasAnyMatch(date)){
            return;
        }
        super.simulateMatches(date);

        int roundIndex = Arrays.asList(PlayoffRound.values()).indexOf(currentRound);
        if(roundIndex>1){//If we have passed the semifinals, dont generate any matches
            return;
        }
        currentRound = PlayoffRound.values()[roundIndex+1];
        generateMatches(date.plusDays(ROUND_GAP));
    }


    public void generateMatches(LocalDate intervalStart) {
        for (int i = getMatchList().size()-1; i >= getMatchList().size()- currentRound.getMatches()*2; i -=2) {
            //Generate matches between the winners of the previous playoff round
            addMatch(intervalStart,getMatchList().get(i-1).getWinner(),getMatchList().get(i).getWinner(),currentRound);
        }
    }

    @Override
    public String toString() {
        return "Playoffs, " + currentRound.toString().toLowerCase()+"s";
    }
}
