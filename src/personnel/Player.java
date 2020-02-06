package personnel;

import java.time.LocalDate;

/**
 * Class specifying information and functionality surrounding Players in teams
 */
public class Player extends AbstractPersonell
{

    private LocalDate birthday;
    private int age;
    private int offensiveSkill;
    private int deffensiveSkill;
    private int goalieSkill;
    private PlayerRole role;
    private String firstName;
    private String surname;

    public Player(String firstName, String surname, LocalDate birthday, int offensiveSkill, int deffensiveSkill,int goalieSkill, PlayerRole role) {
        //Call the super constructor passing the players salary based on it's skill lvls
        super((offensiveSkill+deffensiveSkill+goalieSkill));
        this.firstName = firstName;
        this.surname = surname;
        this.offensiveSkill = offensiveSkill;
        this.deffensiveSkill = deffensiveSkill;
        this.goalieSkill = goalieSkill;
        this.role = role;
        this.birthday = birthday;
        this.age = 0; //Current age is later set and updated based on the current date in the game

    }


    public PlayerRole getRole() {
        return role;
    }

    public void setGoalieSkill(int skillLevel){
        goalieSkill = skillLevel;
        if(goalieSkill > 100){
            goalieSkill = 100;
        }
        else if(goalieSkill < 0){
            goalieSkill = 0;
        }
    }

    public String getName() {
        return firstName + " " + surname;
    }


    public int getGoalieSkill() {
        return goalieSkill;
    }

    public int getOffensiveSkill() {
        return offensiveSkill;
    }

    public int getDeffensiveSkill() {
        return deffensiveSkill;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
