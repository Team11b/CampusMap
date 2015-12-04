package WPI.CampusMap.UI;

public class UIMode {
	//Singleton
	private static UIMode instance;
	
	public static UIMode getInstance()
	{
		return instance;
	}
	
	//other members of class go here
	private String currentMode;
	public static final String DEV_MODE = "devmode";
	public static final String USER_MODE = "usermode";
	
	public UIMode(){
		this.instance = this;
		this.currentMode = USER_MODE;
	}
	
	public String getCurrentMode(){
		return currentMode;
	}	
	
	public void switchCurrentMode(){
		if(currentMode.equals(USER_MODE)){
			currentMode = DEV_MODE;
			DevMode.getInstance().onDevModeEntered();
			//Calls button wrapper
		}			
		else{
			currentMode = USER_MODE;
			UserMode.getInstance().onUserModeEntered();
			//Calls button wrapper
		}
	}

}
