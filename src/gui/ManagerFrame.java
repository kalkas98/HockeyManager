package gui;
import manager.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JFrame for the game
 */
public class ManagerFrame extends JFrame {


    public ManagerFrame(MainMenuPanel mainMenu) {
        super("Hockey manager.Manager 2018");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            System.exit(0);
        }

        CardLayout cardLayout = new CardLayout();
        this.getContentPane().setLayout(cardLayout);
        this.add(mainMenu,"mainMenu");
        cardLayout.show(this.getContentPane(),"mainMenu");

        this.pack();
        this.setResizable(false);
        this.setVisible(true);


        mainMenu.getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(null,"What is your name?", "Hockey manager.Manager",JOptionPane.QUESTION_MESSAGE);
                String teamName = JOptionPane.showInputDialog(null,"What is your team name?", "Hockey manager.Manager",JOptionPane.QUESTION_MESSAGE);

                if(playerName == null || playerName.isEmpty()){//Standard names if no input is given
                    playerName = "Player";
                }
                if(teamName == null || teamName.isEmpty()){
                    teamName = "The Humans";
                }

                mainMenu.getParent().add(new GamePanel(new Manager(playerName,teamName)),"GamePanel");//Add a game panel
                cardLayout.show(mainMenu.getParent(),"GamePanel");//Switch frome the mainMenu panel to the game panel
            }
        });

    }
}
