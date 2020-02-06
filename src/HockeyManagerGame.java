import gui.MainMenuPanel;
import gui.ManagerFrame;

/**
 * Main class
 */
public final class HockeyManagerGame
{

    private HockeyManagerGame() {
    }

    public static void main(String[] args) {
	MainMenuPanel mainMenu = new MainMenuPanel();
	// Re: Variable 'frame' is never used
	// Frame is never used later in the program but creating it is necessary for displaying a window frame
	ManagerFrame frame = new ManagerFrame(mainMenu);
    }
}
