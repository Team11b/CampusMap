package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.*;

public class UserMode extends UIMode{
	//Singleton
	private static UserMode instance;
	
	private UserGraphicalMap graphicalMap;
	
	public UserMode(AppMainWindow window)
	{
		super(window);	
	}
	
	public static UserMode getInstance()
	{
		return null;
	}
	
	@Override
	public void onModeEntered(){
		//Execute changes to UI
		
	}
	
	public void onRouteButton()
	{
		System.out.println("Route me");
	}
	
	public void onClearButton(){
		//destinations.resetLastPoint();
		UserPointGraphicsObject.clearSelected();
		UserPathGraphicsObject.deleteAll();
	}
	
	public void onAddDest(String destName)
	{
		System.out.println("AddDest");
	}
	
	public void onPointAddedToRoute(IPoint newPoint)
	{
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

	@Override
	public void onDraw(Graphics2D graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseClickMap(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseEnterMap(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseLeaveMap(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMoveOverMap(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDraggedOverMap(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMap(String mapName)
	{
	}

	
	
}
