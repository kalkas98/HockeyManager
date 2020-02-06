package gui;

import manager.Manager;
import manager.ManagerListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Class used to create a side menu gui for the game
 * Contains buttons for switching between cards in the cardlayout and buttons for simulating matches
 * and advancing the date
 */
public class SideMenu extends JPanel implements ManagerListener
{
    private SideMenuButton teamButton;
    private SideMenuButton calendarButton;
    private SideMenuButton nextDayButton;
    private SideMenuButton simButton;
    private SideMenuButton tableButton;
    private JLabel dateLabel;
    private JLabel matchLabel;
    private JLabel moneyLabel;
    private JLabel seasonLabel;
    private Manager manager;

    public SideMenu(Manager manager) {
        super(new MigLayout());
        this.manager = manager;

        dateLabel = new JLabel(manager.getCurrentDate().toString());
        JLabel nameLabel = new JLabel(manager.getManagerName());
        matchLabel = new JLabel("");
        moneyLabel = new JLabel("Money: " + manager.getTeam().getMoney());
        seasonLabel = new JLabel(manager.getSeason().toString());

        teamButton = new SideMenuButton("Team");
        calendarButton = new SideMenuButton("Calendar");
        nextDayButton = new SideMenuButton("Next Day");
        simButton = new SideMenuButton("Simluate Match");
        tableButton = new SideMenuButton("Standings");

        this.add(nameLabel,"wrap");
        this.add(moneyLabel, "wrap");
        this.add(matchLabel,"wrap");
        this.add(seasonLabel,"wrap");
        this.add(dateLabel,"wrap");
        this.add(teamButton, "wrap, w 200!, gapright unrelated");
        this.add(calendarButton, "wrap, w 200!");
        this.add(tableButton, "wrap, w 200!");
        this.add(nextDayButton, "wrap, w 200!, gaptop unrelated");
        this.add(simButton, "wrap, w 200!");

        manager.addManagerListener(this);
        this.setVisible(true);

    }

    public SideMenuButton getTeamButton() {
        return teamButton;
    }

    public SideMenuButton getCalendarButton() {
        return calendarButton;
    }

    public SideMenuButton getSimButton() {
        return simButton;
    }

    public SideMenuButton getNextDayButton() {
        return nextDayButton;
    }

    public void setDateLabel(String date) {
        dateLabel.setText(date);
    }

    public SideMenuButton getTableButton() {
        return tableButton;
    }

    public void setMatchLabel(String text) {
        this.matchLabel.setText(text);

    }

    public void setSeasonLabel(String season) {
        this.seasonLabel.setText(season);
    }

    public void setMoneyLabel(String amount){
        this.moneyLabel.setText(amount);
    }

    @Override
    public void statusChanged() {

        boolean hasUnplayedMatch = manager.getSeason().hasMatch(manager.getTeam(),manager.getCurrentDate())
                && !manager.getCurrentMatch().hasEnded();
        //set match label to display the latsest result or the current game
        if(hasUnplayedMatch){
            setMatchLabel(manager.getCurrentMatch().toString());
        }
        else if(manager.getCurrentMatch() != null && manager.getCurrentMatch().hasEnded()){
            setMatchLabel(manager.getLatestResult());
        }

        setMoneyLabel("Money: "+ String.valueOf(manager.getTeam().getMoney()));
        setDateLabel(manager.getCurrentDate().toString());
        setSeasonLabel(manager.getSeason().toString());
        simButton.setEnabled(hasUnplayedMatch);
        nextDayButton.setEnabled(!simButton.isEnabled());
    }
}
