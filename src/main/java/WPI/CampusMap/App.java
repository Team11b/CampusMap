package WPI.CampusMap;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import WPI.CampusMap.Frontend.UI.AppMainWindow;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args)
	{
		try {
			if (System.getProperty("os.name").startsWith("Windows"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			@SuppressWarnings("unused")
			AppMainWindow uiInstance = new AppMainWindow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
