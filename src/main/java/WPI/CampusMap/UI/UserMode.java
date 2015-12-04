package WPI.CampusMap.UI;

public class UserMode {
	//Singleton
	private static UserMode instance;
	
	public static UserMode getInstance()
	{
		return instance;
	}
	
	public UserMode(){
		this.instance = this;		
	}
}
