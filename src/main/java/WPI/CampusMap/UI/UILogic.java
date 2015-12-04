package WPI.CampusMap.UI;

public class UILogic {
	//Singleton
	private static UILogic instance;
	
	public static UILogic getInstance()
	{
		return instance;
	}
	
	//other members of class go here
	private boolean devMode = false; //false is usermode true is devmode
	
	public UILogic(){
		this.instance = this;		
	}
	
	public boolean getDevMode(){
		return devMode;
	}
	
	public void setUser(){
		devMode = false;
	}
	
	public void setDev(){
		devMode = true;
	}

}
