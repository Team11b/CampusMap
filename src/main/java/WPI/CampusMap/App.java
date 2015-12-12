package WPI.CampusMap;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.AppMainWindow;

/**
 * App class containing the main
 *
 */
public class App {
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		if (System.getProperty("os.name").startsWith("Windows"))
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		@SuppressWarnings("unused")
		AppMainWindow uiInstance = new AppMainWindow();
	}
}
