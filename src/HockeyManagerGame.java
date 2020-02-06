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
	ManagerFrame frame = new ManagerFrame(mainMenu);
    }
}
