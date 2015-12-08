package WPI.CampusMap.Backend.Additional.InternetFeatures.SMS;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.AppUserModeControl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
 
import java.util.ArrayList;
import java.util.List;

public class SMSClient {
	 public static final String ACCOUNT_SID = "AC56aa68555da13ed4a250116aac6a1e53";
	 public static final String AUTH_TOKEN = "f1b36697734210d80f9225cc5a404a3a"; 
	 private static TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	 public static void SendText(String to, String textMessage) throws TwilioRestException{
		    // Build a filter for the MessageList
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
		    //params.add(new BasicNameValuePair("Body", AppUserModeControl.getDirections()));
		    params.add(new BasicNameValuePair("Body", textMessage));
		    params.add(new BasicNameValuePair("To", to));
		    params.add(new BasicNameValuePair("From", "+16264145940"));
		 
		    MessageFactory messageFactory = client.getAccount().getMessageFactory();
		    Message message = messageFactory.create(params);
		    System.out.println(message.getSid());
	 }
}
