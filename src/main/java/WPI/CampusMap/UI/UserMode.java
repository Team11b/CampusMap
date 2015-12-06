package WPI.CampusMap.UI;

public class UserMode extends UIMode{
	//Singleton
	private static UserMode instance;
	
	public static UserMode getInstance()
	{
		return instance;
	}
	
	public UserMode(){
		this.instance = this;		
		
	}
	
	public void onUserModeEntered(){
		//Execute changes to UI
	}
	
	public void onRouteButton(){
		System.out.println("Route me");
	}
	
	public void onAddDest(){
		System.out.println("Route me");
	}
	
	public void onWeatherChosen(int index){
		
	}
	
	public void onPrint(){
		
	}	
	
	public void onPdf(){
		
	}
	
    public void onTxt(){
		
	}
    
    public void onEmail(){
    	
    }
    
    public void onSMS(){
    	
    }
    
    public void outputDirections(){
    	
    }

	
	
}
