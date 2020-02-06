package personnel;

/**
 * Enumerate for diffrent types of coaches
 *
 */
public enum CoachType {
    /**
     *  Enum constant for head coach wihch gives a bonus to all types of skills in the team
     */
    COACH(1,5,5,5,600,25000),
    /**
     * Enum constant for a cheaper coach than COACH but on that also gives a smaller bonus to all skills in a team
     */
    ASSISTANT_COACH(2,2,2,2,300,13000),
    /**
     * Enum constant for goaliecoach which gives a stat bonus to a teams starting goalkeeper
     */
    GOALIECOACH(1,0,0,10,400,21000) ;

    private final int maxAmount;//Max amount a team can have of a certain type of coach

    private final int offensiveSkillBoost;//Skill bonuses to teams
    private final int defensiveSkillBoost;
    private final int goalieSkillBoost;

    private final int salary;
    private final int price;

    CoachType(int maxAmount, int offensiveSkillBoost, int defensiveSkillBoost, int goalieSkillBoost, int salary, int price) {
        this.maxAmount = maxAmount;
        this.offensiveSkillBoost = offensiveSkillBoost;
        this.defensiveSkillBoost = defensiveSkillBoost;
        this.goalieSkillBoost = goalieSkillBoost;
        this.salary = salary;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getSalary() {
        return salary;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getOffensiveSkillBoost() {
        return offensiveSkillBoost;
    }

    public int getDefensiveSkillBoost() {
        return defensiveSkillBoost;
    }

    public int getGoalieSkillBoost() {
        return goalieSkillBoost;
    }
}
