package gui;

import manager.Manager;
import manager.ManagerListener;
import personnel.Coach;
import personnel.CoachType;
import personnel.Player;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Specifies information about the gui panel that displays information about the managers team and its players.
 */
public class TeamPanel extends PanelCard implements ManagerListener
{
    private static final int STANDARD_LINEUP_SIZE = 18;
    private static final Font TEXT_AREA_FONT = new Font("Arial", Font.BOLD, 15);
    private JTextArea teamInfo;
    private JTextArea playerInfo;
    private Manager manager;
    private JList<String> teamList;
    private DefaultListModel<String> listModel;
    private JScrollPane teamScrollBar;

    private JButton hireGoalieCoach;
    private JButton hireHeadCoach;
    private JButton hireAssCoach;

    public TeamPanel(Manager manager) {
        super();
        this.manager = manager;

        listModel = new DefaultListModel<>();
        teamList = new JList<>(listModel);//gui list of players in the team
        teamScrollBar = new JScrollPane();
        initTeamList();

        JLabel teamTitel = new JLabel(manager.getTeam().getName());
        playerInfo = new JTextArea();

        JButton setGoalieButton = new JButton("Set starting goalie");
        hireHeadCoach = new JButton("Hire head coach");
        hireHeadCoach.setEnabled(false);
        hireAssCoach = new JButton("Hire assistant coach");
        hireAssCoach.setEnabled(false);
        hireGoalieCoach = new JButton("Hire goalie coach");
        hireGoalieCoach.setEnabled(false);


        teamInfo = new JTextArea("Total team skill: \nDefensive skill:"+ manager.getTeam().getDefensiveSkill() +
                "\nOffensive skill: "+manager.getTeam().getOffensiveSkill());

        initTextArea(teamInfo);
        initTextArea(playerInfo);

        this.add(teamTitel, " wrap, span 2 1");
        this.add(teamScrollBar, " span 2 7");//Span vertically the number of buttons plus 2
        this.add(playerInfo, "span 2 1,wrap");
        this.add(teamInfo,"wrap");
        this.add(setGoalieButton,"wrap");
        this.add(hireHeadCoach,"wrap,gaptop unrelated");
        this.add(hireAssCoach,"wrap");
        this.add(hireGoalieCoach,"wrap");
        manager.addManagerListener(this);


        setGoalieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player selectedPlayer = manager.getTeam().getPlayerList().get(teamList.getSelectedIndex());
                manager.getTeam().setStarterGoalie(selectedPlayer);
                updatePlayerInfo(selectedPlayer);
            }
        });

        hireHeadCoach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.getTeam().addCoach(new Coach(CoachType.COACH));
                if(manager.getTeam().hasMaxOfCoachType(CoachType.COACH)){
                    hireHeadCoach.setEnabled(false);
                }
                updateTeamInfo();
                manager.notifyListeners();

            }
        });

        hireAssCoach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.getTeam().addCoach(new Coach(CoachType.ASSISTANT_COACH));
                if(manager.getTeam().hasMaxOfCoachType(CoachType.ASSISTANT_COACH)){
                    hireAssCoach.setEnabled(false);
                }
                updateTeamInfo();
                manager.notifyListeners();

            }
        });

        hireGoalieCoach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.getTeam().addCoach(new Coach(CoachType.GOALIECOACH));
                if(manager.getTeam().hasMaxOfCoachType(CoachType.GOALIECOACH)){
                    hireGoalieCoach.setEnabled(false);
                }
                Player selectedPlayer = manager.getTeam().getPlayerList().get(teamList.getSelectedIndex());
                updatePlayerInfo(selectedPlayer);
                manager.notifyListeners();
            }
        });



        teamList.addListSelectionListener(new ListSelectionListener()
        {
            @Override public void valueChanged(final ListSelectionEvent e) {
                int playerIndex = teamList.getSelectedIndex();
                Player selectedPlayer = manager.getTeam().getPlayerList().get(playerIndex);
                updatePlayerInfo(selectedPlayer);

            }
        });
        teamList.setSelectedIndex(0);

    }

    public void initTeamList(){
        for (Player player: manager.getTeam().getPlayerList()) {//Add all players to JList
            listModel.addElement(player.getName()+" ");
        }
        teamList.setFont(TEXT_AREA_FONT);
        teamList.setVisibleRowCount(STANDARD_LINEUP_SIZE);
        teamScrollBar.setViewportView(teamList);
    }

    public void initTextArea(JTextArea textArea){
        textArea.setEnabled(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setForeground(Color.BLACK);
    }

    public void updatePlayerInfo(Player player){
        playerInfo.setText(player.getName());
        playerInfo.append("\n"+"Age: "+String.valueOf(player.getAge()) );
        if(player.equals(manager.getTeam().getStarterGoalie())){
            playerInfo.append("\n"+"Role: "+ "Starter Goalie");
        }
        else{
            playerInfo.append("\n"+"Role: "+String.valueOf(player.getRole()));
        }
        playerInfo.append("\n"+"Offensive skill: " + String.valueOf(player.getOffensiveSkill()));
        playerInfo.append("\n"+"Defensive skill: "+String.valueOf(player.getDeffensiveSkill()) );
        playerInfo.append("\n"+"Goalie skill: "+String.valueOf(player.getGoalieSkill()) );
        playerInfo.append("\n"+"Birthday: "+ player.getBirthday()) ;

    }

    public void updateTeamInfo(){
        teamInfo.setText("Total team skill: \nDefensive skill:"+ manager.getTeam().getDefensiveSkill() + "\nOffensive skill: "+manager.getTeam().getOffensiveSkill());
    }

    @Override
    public void statusChanged() {
        int money = manager.getTeam().getMoney();//Check if player can afford to hire a coach
        hireHeadCoach.setEnabled(money >= CoachType.COACH.getPrice()&&
                !manager.getTeam().hasMaxOfCoachType(CoachType.COACH));

        hireAssCoach.setEnabled(money >= CoachType.ASSISTANT_COACH.getPrice()&&
                !manager.getTeam().hasMaxOfCoachType(CoachType.ASSISTANT_COACH));

        hireGoalieCoach.setEnabled(money >= CoachType.GOALIECOACH.getPrice() &&
                !manager.getTeam().hasMaxOfCoachType(CoachType.GOALIECOACH));

    }
}
