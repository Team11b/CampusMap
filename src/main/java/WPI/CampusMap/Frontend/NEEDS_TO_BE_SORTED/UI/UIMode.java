package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;

import javax.xml.stream.XMLStreamException;

abstract public class UIMode {
	
	//other members of class go here	
	public static final String DEV_MODE = "devmode";
	public static final String USER_MODE = "usermode";
	private static String currentGUIMode =  USER_MODE;
	
	private AppMainWindow window;
	
	public UIMode(AppMainWindow window)
	{		
		this.window = window;
	}
	
	public static String getCurrentMode(){
		return currentGUIMode;
	}	
	
	public static void switchCurrentMode(){
		onAnyModeLoad(); 
		if(currentGUIMode.equals(USER_MODE)){
			currentGUIMode = DEV_MODE;
			DevMode.getInstance().onModeEntered();			
		}			
		else{
			currentGUIMode = USER_MODE;
			UserMode.getInstance().onModeEntered();
		}
	}

	protected void onModeEntered(){
	    //overwritten
	}	
	
	private static void onAnyModeLoad(){
		//reintialize code that move modes need reinitializing
	}
	
	public void onBuildingChosen(String building){
		System.out.println("Building chosen");
	}
	
	public static void onFloorChosen(int floor){
		System.out.println("Floor chosen");
	}
	
	private void loadMap(String mapName)
	{
		//mapPanel.loadMap(mapName);
	}
	
	public abstract void onDraw(Graphics2D graphics);
}
