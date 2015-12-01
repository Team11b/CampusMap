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
    	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    	AppUIObject uiInstance = new AppUIObject();
    }
}
