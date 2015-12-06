package WPI.CampusMap.UI;

abstract public class UIMode {
	
	//other members of class go here
	private String currentGUIMode;
	public static final String DEV_MODE = "devmode";
	public static final String USER_MODE = "usermode";
	
	public UIMode(){		
		this.currentGUIMode = USER_MODE;
	}
	
	public String getCurrentMode(){
		return currentGUIMode;
	}	
	
	protected void switchCurrentMode(){
		onAnyModeLoad(); 
		if(currentGUIMode.equals(USER_MODE)){
			currentGUIMode = DEV_MODE;
			DevMode.getInstance().onModeEntered();			
		}			
		else{
			currentGUIMode = USER_MODE;			
		}
	}

	protected void onModeEntered(){
	    //overwritten
	}	
	
	private void onAnyModeLoad(){
		//reintialize code that move modes need reinitializing
	}
	
	public void onBuildingChosen(String building){
		
	}
	
	public void onFloorChosen(int floor){
		
	}	
	

}
