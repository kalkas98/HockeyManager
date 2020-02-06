package seasons;


/**
 * Enumerate for the possible stages/rounds of the playoff, specifies the max amount of matches for each playoff round
 */
public enum PlayoffRound {
    /**
     * constant for the quarterfinal round
     */
    QUARTERFINAL(4, 30000),
    /**
     * constant for the semifinal round
     */
    SEMIFINAL(2,50000),
    /**
     * constant for the final round
     */
    FINAL(1,100000),
    /**
     * constant for when the palyoff has ended
     */
    ENDED(0,0);

    private final int matches;
    private final int moneyReward;

    public int getMoneyReward() {
        return moneyReward;
    }

    public int getMatches() {
        return matches;
    }

    PlayoffRound(int matches, int reward) {
        this.moneyReward = reward;
        this.matches = matches;
    }
}
