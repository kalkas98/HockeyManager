package gui;

import manager.Manager;
import manager.ManagerListener;
import matches.MatchOutcome;
import seasons.LeagueSeason;
import seasons.PlayoffSeason;
import seasons.Season;
import team.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Class used to create a panel where the current league standings can be seen in a table
 *
 */
public class TablePanel extends PanelCard implements ManagerListener
{

    private static final int COLUMN_COUNT = 5;
    private static final int ROW_HEIGHT = 20;
    private static final int NAME_COLUMN_WIDTH = 100;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private Season season;
    private Manager manager;

    public TablePanel(Manager manager) {
        super();
        tableModel = new DefaultTableModel(){
            @Override public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        this.manager = manager;
        this.season = manager.getSeason();
        this.table = new JTable(tableModel);
        this.scrollPane = new JScrollPane();
        this.add(scrollPane);
        manager.addManagerListener(this);
        initTable();

    }

    public void initTable(){
        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(500,(ROW_HEIGHT *(season.getTeamList().size() +1))));

        tableModel.addColumn("Team");
        tableModel.addColumn("Wins");
        tableModel.addColumn("Ties");
        tableModel.addColumn("Losses");
        tableModel.addColumn("Points");
        table.setRowHeight(ROW_HEIGHT);
        tableModel.setRowCount(season.getTeamList().size());
        tableModel.setColumnCount(COLUMN_COUNT);

        table.setColumnSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        uppdateTable();
        table.getColumnModel().getColumn(0).setMinWidth(NAME_COLUMN_WIDTH);
    }


    public void clearTable(){
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 1; j < table.getColumnCount(); j++) {
                table.setValueAt(Integer.valueOf(0),i,j);
            }
        }
    }

    public void setSeason(Season season) {
        if(season.getClass().equals(LeagueSeason.class)){
            season.resetTeamStandings();
            clearTable();
        }
        this.season = season;
    }


    public void uppdateTable(){

        int rowCount = 0;
        for (Team team: season.getTeamList()) {
            table.setValueAt(Integer.valueOf(team.getPoints()),rowCount,4);

            table.setValueAt(team.getOutcomes().get(MatchOutcome.LOSS), rowCount, 3);
            table.setValueAt(team.getOutcomes().get(MatchOutcome.TIE),rowCount,2);
            table.setValueAt(team.getOutcomes().get(MatchOutcome.WIN),rowCount,1);
            table.setValueAt(team.getName(),rowCount,0);
            rowCount++;


        }
    }

    @Override
    public void statusChanged() {
        if(!season.equals(manager.getSeason())){//Check if season has changed
            setSeason(manager.getSeason());
        }
        if(!season.getClass().equals(PlayoffSeason.class)){
            uppdateTable();
        }

    }
}
