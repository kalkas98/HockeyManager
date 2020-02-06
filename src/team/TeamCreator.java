package team;

import personnel.Player;
import personnel.PlayerRole;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class used to create random teams
 */
public final class TeamCreator
{
	private static final int BIRTH_CEILING = 2000;
	private static final int BIRTH_FLOOR = 1980;
	private static final int RANDOM_SKILL_POINTS = 30;

	private static String[] firstNames = {
			"JAMES",
			"JOHN",
			"ROBERT",
			"MICHAEL",
			"WILLIAM",
			"DAVID",
			"RICHARD",
			"CHARLES",
			"JOSEPH",
			"THOMAS",
			"CHRISTOPHER",
			"DANIEL",
			"PAUL",
			"MARK",
			"DONALD",
			"GEORGE",
			"KENNETH",
			"STEVEN",
			"EDWARD",
			"BRIAN",
			"RONALD",
			"ANTHONY",
			"KEVIN",
			"JASON",
			"MATTHEW",
			"GARY",
			"TIMOTHY",
			"JOSE",
			"LARRY",
			"JEFFREY",
			"FRANK",
			"SCOTT",
			"ERIC",
			"STEPHEN",
			"ANDREW",
			"RAYMOND",
			"GREGORY",
			"JOSHUA",
			"JERRY",
			"DENNIS",
			"WALTER",
			"PATRICK",
			"PETER",
			"HAROLD",
			"DOUGLAS",
			"HENRY",
			"CARL",
			"ARTHUR",
			"RYAN",
			"ROGER",
			"JOE"};
	private static String[] surnames = {
			"SMITH",
			"JOHNSON",
			"WILLIAMS",
			"JONES",
			"BROWN",
			"DAVIS",
			"MILLER",
			"WILSON",
			"MOORE",
			"TAYLOR",
			"ANDERSON",
			"THOMAS",
			"JACKSON",
			"WHITE",
			"HARRIS",
			"MARTIN",
			"THOMPSON",
			"GARCIA",
			"MARTINEZ",
			"ROBINSON",
			"CLARK",
			"RODRIGUEZ",
			"LEWIS",
			"LEE",
			"WALKER",
			"HALL",
			"ALLEN",
			"YOUNG",
			"HERNANDEZ",
			"KING",
			"WRIGHT",
			"LOPEZ",
			"HILL",
			"SCOTT",
			"GREEN",
			"ADAMS",
			"BAKER",
			"GONZALEZ",
			"NELSON",
			"CARTER",
			"MITCHELL",
			"PEREZ",
			"ROBERTS",
			"TURNER",
			"PHILLIPS",
			"CAMPBELL",
			"PARKER",
			"EVANS",
			"EDWARDS",
			"COLLINS",
			"STEWART" };



	private TeamCreator() {
	}

	/**
	 * Generates players with random names, brithdays and skills to a list
	 *
	 * @param role PlayerRole for the players that will be created
	 * @param playerList list to add players to
	 */
	private static void addPlayerGroup(PlayerRole role, List<Player> playerList){
		for (int i = 0; i < role.getTroopLimit(); i++) {
			playerList.add(createRandomPlayer(role));
		}
	}

	private static Player createRandomPlayer(PlayerRole role){
		Random random = new Random();
		int nameIndex = random.nextInt(firstNames.length);
		int surnameIndex = random.nextInt(surnames.length);

		int offSkill = role.getOffSkillFloor() + random.nextInt(RANDOM_SKILL_POINTS)+1;
		int defSkill = role.getDefSkillFloor() + random.nextInt(RANDOM_SKILL_POINTS)+1;
		int goalieSkill = role.getGoalieSkillFloor() + random.nextInt(RANDOM_SKILL_POINTS)+1;

		Month birthMonth = Month.values()[random.nextInt(Month.values().length)];
		int birthYear = BIRTH_FLOOR + random.nextInt(BIRTH_CEILING - BIRTH_FLOOR);
		LocalDate birthday = LocalDate.of(birthYear, birthMonth, 1 + random.nextInt(birthMonth.minLength()));

		Player player = new Player(firstNames[nameIndex], surnames[surnameIndex], birthday, offSkill, defSkill,
				goalieSkill, role);
		return player;
	}

	public static Team createRandomTeam(boolean ai, String name){
		List<Player> playerList = new ArrayList<>();
		for (PlayerRole role : PlayerRole.values()) {
			TeamCreator.addPlayerGroup(role, playerList);
		}
		Team newTeam = new Team(playerList, name, ai);
		return newTeam;
	}

	public static List<Team> createRandomTeams(int amount){
		List<Team> teams = new ArrayList<>();
		for (int i = 0; i < amount ; i++) {
			teams.add(createRandomTeam(true, "Team "+(i+1)));
		}
		return teams;
	}
}

