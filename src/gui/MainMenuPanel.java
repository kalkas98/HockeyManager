package gui;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;



/**
 * Class for the main menu in the game
 *
 */
public class MainMenuPanel extends JPanel {
    private static final int TITLE_FONT_SIZE = 40;
    private static final int BUTTON_FONT_SIZE = 30;

    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 800;


    private JButton newGameButton;

    public MainMenuPanel() {
        super(new MigLayout("fillx"));
        Font buttonFont = new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE);
        JLabel titleLabel = new JLabel("Hockey manager.Manager 2018");
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(buttonFont);
        this.add(titleLabel,"wrap, al center center");
        this.add(newGameButton,"al center center, wrap, w 300!");
        this.setVisible(true);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));




    }

    public JButton getNewGameButton() {
        return newGameButton;
    }


}
