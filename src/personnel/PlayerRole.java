package personnel;

/**
 * Enumerate for player roles, defines the max and minimum skill lvl for each role as well as the maximum players of a
 * certain role a team can contain.
 */
public enum PlayerRole
{
    /**
     * Enum constant for the forward role
     */
    FORWARD(40,70,0,8),
    /**
     * Enum constant for the center role
     */
    CENTER(50,65,0,4),
    /**
     * Enum constant for the defender role
     */
    DEFENDER(70,50,0,6),
    /**
     * Enum cosntant for the goalkeeper role
     */
    GOALKEEPER(0,0,70,2);

    private final int defSkillFloor;
    private final int offSkillFloor;
    private final int troopLimit;
    private final int goalieSkillFloor;

    PlayerRole(int defSkillFloor, int offSkillFloor,int goalieSkillFloor, int troopLimit) {
        this.defSkillFloor = defSkillFloor;
        this.offSkillFloor = offSkillFloor;
        this.troopLimit = troopLimit;
        this.goalieSkillFloor = goalieSkillFloor;
    }

    public int getGoalieSkillFloor() {
        return goalieSkillFloor;
    }

    public int getDefSkillFloor() {
        return defSkillFloor;
    }

    public int getOffSkillFloor() {
        return offSkillFloor;
    }

    public int getTroopLimit() {
        return troopLimit;
    }
}
