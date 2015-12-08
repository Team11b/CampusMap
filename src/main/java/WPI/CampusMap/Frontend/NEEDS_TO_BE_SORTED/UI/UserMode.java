package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.twilio.sdk.TwilioRestException;

import WPI.CampusMap.Backend.Additional.InternetFeatures.SMS.SMSClient;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.*;

public class UserMode extends UIMode {
	// Singleton
	private static UserMode instance;

	private UserGraphicalMap graphicalMap;

	public UserMode(AppMainWindow window) {
		super(window);
	}

	public static UserMode getInstance() {
		return null;
	}

	@Override
	public void onModeEntered() {
		// Execute changes to UI

	}

	public void onRouteButton() {
		System.out.println("Route me");
	}

	public void onClearButton() {
		// destinations.resetLastPoint();
		UserPointGraphicsObject.clearSelected();
		UserPathGraphicsObject.deleteAll();
	}

	public void onAddDest(String destName) {
		System.out.println("AddDest");
	}

	public void onPointAddedToRoute(IPoint newPoint) {
	}

	public void onWeatherChosen(String option) {
		System.out.println("Weather chosen is " + option);
	}

	public void onPrint() {
		System.out.println("Print");
	}

	public void onPdf() {
		System.out.println("PDF");
	}

	public static void onTxt() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(chooser);
		File destination = chooser.getSelectedFile();
		FileWriter write;
		// String directions = AppUserModeControl.getDirections();
		try {
			write = new FileWriter(destination);
			PrintWriter printLine = new PrintWriter(write);
			printLine.print("This is a test.");
			printLine.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("TXT");
	}

	public static void onEmail() {
		Email email = new SimpleEmail();
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("team0011b@gmail.com", "SoftEng15"));
		// email.setSSLOnConnect(true);
		try {
			email.getMailSession().getProperties().put("mail.smtp.auth", "true");
			email.getMailSession().getProperties().put("mail.debug", "true");
			email.getMailSession().getProperties().put("mail.smtp.port", "587");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "587");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			email.setFrom("team0011b@gmail.com", "Team 0011b");
			email.setSubject("Campus Directions");
			email.setMsg(AppUserModeControl.getDirections());
			email.addTo("wespurgeon@wpi.edu", "WPI Campus Map User");
			email.send();
			System.out.println("Email sent");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void onSMS() {
		System.out.println("SMS");
		try {
			SMSClient.SendText("18184411799", "HELLO WORLD");
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void outputDirections() {

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
	public void onMouseDraggedOverMap(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadMap(String mapName) {
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

	protected static ActionListener txtExpoterAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			onTxt();
		}
	};
}
