package WPI.CampusMap.Frontend.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class UIMode {
	
	//other members of class go here	
	public static final String DEV_MODE = "devmode";
	public static final String USER_MODE = "usermode";
	@SuppressWarnings("unused")
	private static String currentGUIMode =  USER_MODE;
	
	private static final String DEFAULT_MAP = "Campus_Map";
	
	private AppMainWindow window;
	
	public UIMode(AppMainWindow window)
	{		
		this.window = window;
		window.makeOtherDropDown(DEFAULT_MAP);
	}

	protected void onModeEntered()
	{
		loadMap(DEFAULT_MAP);
	}
	
	protected AppMainWindow getWindow()
	{
		return window;
	}
	
	public abstract void gotoPoint(String name);
	
	public abstract void loadMap(String mapName);
	
	public abstract void onDraw(Graphics2D graphics);
	
	public abstract void onMouseClickMap(MouseEvent e);
	
	public abstract void onMousePressedMap(MouseEvent e);
	
	public abstract void onMouseReleaseMap(MouseEvent e);
	
	public abstract void onMouseEnterMap(MouseEvent e);
	
	public abstract void onMouseLeaveMap(MouseEvent e);
	
	public abstract void onMouseMoveOverMap(MouseEvent e);
	
	public abstract void onMouseDraggedOverMap(MouseEvent e);

	public abstract void onMouseScrollOnMap(int unitsToScroll);
}
