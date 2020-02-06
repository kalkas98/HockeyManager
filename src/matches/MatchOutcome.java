package matches;

import java.awt.*;

/**
 * Enum for possible match outcomes. Each enum konstant takes a point reward, a color, and a money reward.
 *
 */
public enum MatchOutcome {
    /**
     * Enum constant for when a team wins a match
     */
    WIN(3, Color.GREEN,20000),
    /**
     * Enum constant for when a match is tied between two teams
     */
    TIE(1,Color.YELLOW,12000),
    /**
     * Enum constant for when a match has been lost
     */
    LOSS(0,Color.RED,8000),
    /**
     * Enum constant for when a match is yet to be played
     */
    NOT_PLAYED(0,Color.cyan,0);


    private final int pointReward;
    private final Color color;//Color to be displayed in the calendar for a match outcome
    private final int moneyReward;

    MatchOutcome(int pointReward, Color color, int moneyReward) {
        this.pointReward = pointReward;
        this.color = color;
        this.moneyReward = moneyReward;
    }

    public int getPointReward() {
        return pointReward;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public Color getcolor(){
        return color;
    }

}
