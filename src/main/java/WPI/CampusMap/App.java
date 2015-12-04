package WPI.CampusMap;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import WPI.CampusMap.UI.AppUIObject;

/**
 * Hello world!
 *
 */
public class App
{	
		
    public static void main( String[] args ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
    	if(System.getProperty("os.name").startsWith("Windows"))
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    	@SuppressWarnings("unused")
		AppUIObject uiInstance = new AppUIObject();
    }
}
