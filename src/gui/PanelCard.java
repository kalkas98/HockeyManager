package gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract class containing properties for all FrameCards that will be be in the MainPanels CardLayout
 */
public abstract class PanelCard extends JPanel
{
    protected PanelCard() {
	super(new MigLayout());
	this.setBackground(Color.LIGHT_GRAY);
    }
}
