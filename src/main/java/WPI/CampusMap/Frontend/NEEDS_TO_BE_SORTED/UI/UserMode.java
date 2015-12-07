package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    	// Recipient's email ID needs to be mentioned.
        String to = "wespurgeon@wpi.edu";

        // Sender's email ID needs to be mentioned
        String from = "will@spurgeonworld.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
           // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

           // Set Subject: header field
           message.setSubject("This is the Subject Line!");

           // Now set the actual message
           message.setText("This is actual message");

           // Send message
           Transport.send(message);
           System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
           mex.printStackTrace();
        }
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
