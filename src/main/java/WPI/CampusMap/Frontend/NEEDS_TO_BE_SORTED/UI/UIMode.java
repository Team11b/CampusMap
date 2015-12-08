package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class UIMode {
	
	//other members of class go here	
	public static final String DEV_MODE = "devmode";
	public static final String USER_MODE = "usermode";
	private static String currentGUIMode =  USER_MODE;
	
	private static final String DEFAULT_MAP = "Atwater_Kent-0";
	
	private AppMainWindow window;
	
	public UIMode(AppMainWindow window)
	{		
		this.window = window;
		loadMap(DEFAULT_MAP);
	}

	protected abstract void onModeEntered();
	
	public abstract void loadMap(String mapName);
	
	public abstract void onDraw(Graphics2D graphics);
	
	public abstract void onMouseClickMap(MouseEvent e);
	
	public abstract void onMouseEnterMap(MouseEvent e);
	
	public abstract void onMouseLeaveMap(MouseEvent e);
	
	public abstract void onMouseMoveOverMap(MouseEvent e);
	
	public abstract void onMouseDraggedOverMap(MouseEvent e);
}
