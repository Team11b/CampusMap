package WPI.CampusMap.UI;

public class DevMode {
	//Singleton
	private static DevMode instance;
	
	public static DevMode getInstance()
	{
		return instance;
	}
	
	private String currentMode;
	private String pointID;
	private String pointType;
	
	public static final String SELECT_MODE = "selectmode";
	public static final String PLACE_MODE = "placemode";
	public static final String REMOVE_MODE = "removemode";
	public static final String EDGE_MODE = "edgemode";
	public static final String REMOVE_EDGE_MODE = "removeedgemode";
	
	public DevMode(){
		instance = this;
		currentMode = SELECT_MODE;
	}
	
	public void onDevModeEntered(){
		currentMode = SELECT_MODE;
		UIMode.getInstance().setWindowText("Dev Mode");		
		//Switch label to textbox for scale
		//Show and hide UI elements
	}
	
	

	public void setPlace(){
		if(currentMode != PLACE_MODE){
			currentMode = PLACE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
		}
			
			
	}
	
	public void setRemove(){		
		if(currentMode != REMOVE_MODE){
			currentMode = REMOVE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
		}
	}
	
	public void setEdge(){
		if(currentMode != EDGE_MODE){
			currentMode = EDGE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
		}
	}
	
	public void setRemoveEdge(){
		if(currentMode != REMOVE_EDGE_MODE){
			currentMode = REMOVE_EDGE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
		}
	}
	
	public void save(){
		if(currentMode != SELECT_MODE){
			currentMode = SELECT_MODE;
		}
	}
	
	public void setType(String Type){		
		pointType = Type;
		//set the typeselector
	}
	
	public void setID(String Id){
		pointID = Id;
		//set the textbox
		
	}
	
	public String getCurrentMode(){
		return currentMode; 
	}
	
	public String getType(){
		//pull from typeselector
		return pointType;
	}
	
	public String getID(){
		//pull from idtextbox
		return pointID;
	}
	
	public void updateTypeID(){
		//for saving and switching points after change
	}
	
	public void addConnect(){
		
	}
}
