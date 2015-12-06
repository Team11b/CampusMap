package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.*;

public class UserMode extends UIMode{
	//Singleton
	private static UserMode instance;
	
	public UserMode(){
		this.instance = this;		
	}
	
	public static UserMode getInstance()
	{
		if(instance == null){		
			instance = new UserMode();			
		}
		return instance;
	}
	
	@Override
	public void onModeEntered(){
		//Execute changes to UI
		
	}
	
	public void onRouteButton(){
		System.out.println("Route me");
		/*MultiPath path = UserPointGraphicsObject.route();
		Route route = new Route(path);
		for(Instruction i: route.getRoute()){
			//need directions output function
			//txtDirections.setText(txtDirections.getText() + i.getInstruction());
		}*/
	}
	
	public void onClearButton(){
		//destinations.resetLastPoint();
		UserPointGraphicsObject.clearSelected();
		UserPathGraphicsObject.deleteAll();
	}
	
	public void onAddDest(){
		System.out.println("AddDest");
	}
	
	public void onPointAddedToRoute(IPoint newPoint){
		//destinations.setDestination(newPoint.getId());
	}
	
	public void onWeatherChosen(String option){
		System.out.println("Weather chosen is "+ option);
	}
	
	public void onPrint(){
		System.out.println("Print");
	}	
	
	public void onPdf(){
		System.out.println("PDF");
	}
	
    public void onTxt(){
    	System.out.println("TXT");
	}
    
    public void onEmail(){
    	System.out.println("Email");
    }
    
    public void onSMS(){
    	System.out.println("SMS");
    }
    
    public void outputDirections(){
    	
    }

	
	
}
