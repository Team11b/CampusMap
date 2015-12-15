package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.SMS;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class SMSClient {
	public static final String ACCOUNT_SID = "AC56aa68555da13ed4a250116aac6a1e53";
	public static final String AUTH_TOKEN = "f1b36697734210d80f9225cc5a404a3a"; 
	private static TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);


	private static String popUp() {
		final JFrame parent = new JFrame();
		parent.pack();
		parent.setVisible(true);

		String number = JOptionPane.showInputDialog(parent, "What is your phone number?", null);
		parent.setVisible(false);
		return number;
	}


	public static void SendText(String to, String textMessage) throws TwilioRestException{
		// Build a filter for the MessageList
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String number = popUp();
		
		params.add(new BasicNameValuePair("Body", textMessage));
		params.add(new BasicNameValuePair("To", "+1" + number));
		params.add(new BasicNameValuePair("From", "+16264145940"));

		MessageFactory messageFactory = (MessageFactory) client.getAccount().getSmsFactory();
		Message message = messageFactory.create(params);
		System.out.println(message.getSid());
	}
}
