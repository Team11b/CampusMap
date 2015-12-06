package WPI.CampusMap.UI;

import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Graphics.User.UserPathGraphicsObject;
import WPI.CampusMap.Graphics.User.UserPointGraphicsObject;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.Route.Instruction;
import WPI.CampusMap.PathPlanning.Route.Route;

public class UserMode extends UIMode{
	//Singleton
	private static UserMode instance;
	
	private UserMode(){
		this.instance = this;		
	}
	
	public static UserMode getInstance()
	{
		if(instance != null){		
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
		MultiPath path = UserPointGraphicsObject.route();
		Route route = new Route(path);
		for(Instruction i: route.getRoute()){
			//need directions output function
			//txtDirections.setText(txtDirections.getText() + i.getInstruction());
		}
	}
	
	public void onClearButton(){
		//destinations.resetLastPoint();
		UserPointGraphicsObject.clearSelected();
		UserPathGraphicsObject.deleteAll();
	}
	
	public void onAddDest(){
		System.out.println("AddDest");
	}
	
	public void onPointAddedToRoute(Point newPoint){
		//destinations.setDestination(newPoint.getId());
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
