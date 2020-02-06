package gui;

import manager.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main container containg a side menu where the palyer can advance the game
 * and a cardlayout Jpanel where players can switch between panels
 */
public class GamePanel extends JPanel
{

    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 800;

    public GamePanel(Manager manager) throws HeadlessException {
	super();
	CardLayout cardLayout = new CardLayout();

	SideMenu sideMenu = new SideMenu(manager);
	TeamPanel teamPanel = new TeamPanel(manager);
	CalendarPanel calendarPanel = new CalendarPanel(manager);
	TablePanel tablePanel = new TablePanel(manager);

	JPanel cards = new JPanel(cardLayout);//container for panels

	cards.add(teamPanel, "teamPanel");
	cards.add(calendarPanel, "calendarPanel");
	cards.add(tablePanel, "tablePanel");
	cardLayout.show(cards, "teamPanel");//Show teamPanel when game startss

	this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	this.add(sideMenu);
	this.add(cards);
	this.setPreferredSize(getPreferredSize());
	this.setVisible(true);


	sideMenu.getNextDayButton().addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(ActionEvent e) {
		manager.advanceDate();
	    }
	});
	sideMenu.getTeamButton().addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		cardLayout.show(cards, "teamPanel");
	    }
	});

	sideMenu.getCalendarButton().addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		cardLayout.show(cards, "calendarPanel");
	    }
	});

	sideMenu.getTableButton().addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		cardLayout.show(cards, "tablePanel");
	    }
	});
	sideMenu.getSimButton().addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		manager.simulateTodaysMatches();
	    }
	});

    }

    public Dimension getPreferredSize() {
	return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

}
