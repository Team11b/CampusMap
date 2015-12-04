package WPI.CampusMap.UI;

public class DevMode {
	//Singleton
	private static DevMode instance;
	
	public static DevMode getInstance()
	{
		return instance;
	}
	
	public DevMode(){
		this.instance = this;		
	}

	public void setPlace(){
		
	}
	
	public void setRemove(){		
	
	}
	
	public void setEdge(){
		
	}
	
	public void setRemoveEdge(){
		
	}
}
