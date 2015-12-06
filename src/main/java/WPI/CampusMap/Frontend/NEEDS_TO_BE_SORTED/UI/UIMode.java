package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import javax.xml.stream.XMLStreamException;

abstract public class UIMode {
	
	//other members of class go here	
	public static final String DEV_MODE = "devmode";
	public static final String USER_MODE = "usermode";
	private static String currentGUIMode =  USER_MODE;
	
	public UIMode(){		
		//this.currentGUIMode =
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
	
	private void loadMap(String mapName) throws XMLStreamException {
		//mapPanel.loadMap(mapName);
	}
	
	public void reDrawUI() {
		//mapPanel.repaint();
	}
	

}
