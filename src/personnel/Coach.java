package personnel;

/**
 * Coach class specifying information and functionality for hireable coaches in the game
 */
public class Coach extends AbstractPersonell{
    private CoachType coachType;

    public Coach(CoachType coachType) {
        super(coachType.getSalary());
        this.coachType = coachType;
    }

    public int getDefensiveBoost(){
        return coachType.getDefensiveSkillBoost();
    }
    public int getGoalieBoost(){
        return coachType.getGoalieSkillBoost();
    }
    public int getOffensiveBoost(){
        return coachType.getOffensiveSkillBoost();
    }

    public CoachType getCoachType() {
        return coachType;
    }
}
