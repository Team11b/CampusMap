package WPI.CampusMap.UI;

public class DevMode {
	//Singleton
	private static DevMode instance;
	
	public static DevMode getInstance()
	{
		return instance;
	}
	
	private String currentMode;
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
	}
	
	

	public void setPlace(){
		
	}
	
	public void setRemove(){		
	
	}
	
	public void setEdge(){
		
	}
	
	public void setRemoveEdge(){
		
	}
	
	public void save(){
		
	}
}
