package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.twilio.sdk.TwilioRestException;

import WPI.CampusMap.Backend.Additional.InternetFeatures.SMS.SMSClient;
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
    
    public static void onEmail() {
    	Email email = new SimpleEmail();
    	email.setHostName("smtp.googlemail.com");
    	email.setSmtpPort(465);
    	email.setAuthenticator(new DefaultAuthenticator("team0011b", "SoftEng15"));
    	email.setSSLOnConnect(true);
    	try {
    		email.setFrom("team0011b@gmail.com");
        	email.setSubject("TestMail");
			email.setMsg("This is a test mail ... :-)");
			email.addTo("wespurgeon@wpi.edu");
	    	email.send();
	    	System.out.println("Email sent");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void onSMS(){
    	System.out.println("SMS");
    	try {
			SMSClient.SendText("18184411799", "HELLO WORLD");
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	protected static ActionListener emailAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			onEmail();
		}
	};
	
	protected static ActionListener SMSAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			onSMS();
		}
	};
}
