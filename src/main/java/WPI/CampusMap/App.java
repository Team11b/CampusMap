package WPI.CampusMap;

import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import WPI.CampusMap.Frontend.UI.AppMainWindow;

/**
 * App class containing the main
 *
 */
public class App {

	public static void main(String[] args) {
		try {
			FileOutputStream fs = new FileOutputStream("log.txt");
			PrintStream printStream = new PrintStream(fs);

			System.setOut(printStream);
			System.setErr(printStream);
		} catch (Exception e1) {
			return;
		}

		System.out.println("Starting app...");

		try {
			if (System.getProperty("os.name").startsWith("Windows"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			@SuppressWarnings("unused")
			AppMainWindow uiInstance = new AppMainWindow();
		} catch (Exception e) {
			System.out.println("Fatal Error Occured...");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
