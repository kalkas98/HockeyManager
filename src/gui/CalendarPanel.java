package gui;

import manager.Manager;
import manager.ManagerListener;
import matches.Match;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;


/**
 * Class used to create a calendar panel which displays a calendar showing the current date and match dates, as wel as
 * results for played matches
 */
public class CalendarPanel extends PanelCard implements ManagerListener
{
    private static final int ROW_HEIGHT = 40;
    private static final int ROW_COUNT = 6;
    private static final int COLUMN_COUNT = 7;

    private JLabel dateLabel;
    private JTable calendarTable;
    private DefaultTableModel tableModel;
    private JScrollPane calenderScrollPane;
    private Manager manager;
    private LocalDate displayedDate;

    public CalendarPanel(Manager manager) {
	super();
	displayedDate = Manager.START_DATE;
	tableModel = new DefaultTableModel()
	{
	    @Override public boolean isCellEditable(int row, int column) {
		return false;
	    }
	};
	this.manager = manager;
	calendarTable = new JTable(tableModel);//Calendar to display matches
	calenderScrollPane = new JScrollPane();
	dateLabel = new JLabel(displayedDate.getMonth() + " " + displayedDate.getYear());
	JLabel infoLabel = new JLabel("Green = Win, Red = Loss, Yellow = Tie, Pink = Current Day, Blue = Upcoming match");
	infoLabel.setFont(new Font("Arial", Font.PLAIN, 10));

	JButton next = new JButton("Next");//Buttons to goto next or previous month
	JButton previous = new JButton("Previous");

	next.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		displayedDate = displayedDate.plusMonths(1);
		uppdateCal(displayedDate);
	    }
	});

	previous.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		displayedDate = displayedDate.plusMonths(-1);
		uppdateCal(displayedDate);
	    }
	});

	this.add(dateLabel, "wrap");
	this.add(calendarTable);
	this.add(calenderScrollPane, "wrap, span 2 1");
	this.add(previous, "w 100");
	this.add(next, "align right, w 100,wrap");
	this.add(infoLabel, "span 2 1");
	initCalendar();
	manager.addManagerListener(this);

    }

    public void initCalendar() {
	calenderScrollPane.setViewportView(calendarTable);
	calenderScrollPane.setPreferredSize(new Dimension(400, (ROW_HEIGHT * (ROW_COUNT + 1) - calendarTable.getRowHeight())));

	for (DayOfWeek day : DayOfWeek.values()) {
	    tableModel.addColumn(day.toString().substring(0, 3));//Add MON, TUE, WED... as collumns
	}
	calendarTable.setRowHeight(ROW_HEIGHT);
	tableModel.setRowCount(ROW_COUNT);
	tableModel.setColumnCount(COLUMN_COUNT);

	calendarTable.setColumnSelectionAllowed(true);
	calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	calendarTable.getTableHeader().setReorderingAllowed(false);
	calendarTable.getTableHeader().setResizingAllowed(false);
	calendarTable.setCellSelectionEnabled(false);

	uppdateCal(displayedDate);
    }

    public void clearCal() {
	for (int y = 0; y < ROW_COUNT; y++) {
	    for (int x = 0; x < COLUMN_COUNT; x++) {
		calendarTable.setValueAt("", y, x);
	    }
	}
    }

    public void uppdateCal(LocalDate date) {
	clearCal();
	int firstDayInMonth = date.withDayOfMonth(1).getDayOfWeek().getValue() - 1;//Day as a nr. Monday is 0 Sunday is 6
	int monthDays = date.lengthOfMonth();
	for (int i = 1; i <= monthDays; i++) {//Loop setting the correct day numbers in calendarTable
	    int x = (i + firstDayInMonth - 1) % 7;
	    int y = (i + firstDayInMonth - 1) / 7;
	    calendarTable.setValueAt(Integer.valueOf(i), y, x);
	}

	dateLabel.setText(date.getMonth() + " " + date.getYear());
	calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new CellRenderer());

    }

    @Override public void statusChanged() {
	displayedDate = LocalDate.of(manager.getCurrentDate().getYear(), manager.getCurrentDate().getMonth(),
				     manager.getCurrentDate().getDayOfMonth());
	uppdateCal(manager.getCurrentDate());
    }

    private class CellRenderer extends DefaultTableCellRenderer
    {
	//CellRenderer that renders diffrent colors for various dates
	@Override public Component getTableCellRendererComponent(final JTable table, final Object value,
								 final boolean isSelected, final boolean hasFocus,
								 final int row, final int column)
	{
	    //Get current cell
	    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    if (table.getValueAt(row, column).toString().isEmpty()) {
		//First check if table value is null or empty (makes if statements below more readable).
		cell.setBackground(Color.WHITE);
		return cell;
	    }

	    int day = Integer.valueOf(table.getValueAt(row, column).toString());
	    if (day == manager.getCurrentDate().getDayOfMonth() &&
		manager.getCurrentDate().toString().equals(displayedDate.toString())) {
		//If cell is current date, set cell color to pink
		cell.setBackground(Color.PINK);
	    } else if ((manager.getSeason().hasMatch(manager.getTeam(),
						     LocalDate.of(displayedDate.getYear(), displayedDate.getMonth(), day)))) {

		Match match = manager.getSeason().getMatchByDate(manager.getTeam(), LocalDate
			.of(displayedDate.getYear(), displayedDate.getMonth(), day));
		//If player has a amtch at the date display the color for the outcome of the match
		cell.setBackground(match.getPlayerOutcome().getcolor());
	    } else {
		cell.setBackground(Color.WHITE);
	    }
	    return cell;
	}
    }
}
