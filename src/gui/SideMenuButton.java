package gui;
import javax.swing.*;
import java.awt.*;

/**
 * Class specifying properties for the buttons of the SideMenu JPanel
 */
public class SideMenuButton extends JButton
{

    private static final int FONT_SIZE = 15;

    public SideMenuButton(String text) {
        super(text);
        this.setOpaque(true);
        this.setFocusPainted(false);
        this.setBackground(Color.PINK);
        this.setForeground(Color.BLACK);

        this.setFont(new Font("Monospaced", Font.BOLD, FONT_SIZE));
    }
}
