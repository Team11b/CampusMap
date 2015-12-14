package WPI.CampusMap.InternetTest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.Wunderground;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

public class TestConnection {
	
	@Before
	public void initialize() {

	}

	@Test
	public void testInternetExists(){
		try {
			try {
				URL url = new URL("http://www.google.com");
				System.out.println(url.getHost());
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				assertTrue(con.getResponseCode() == 200);						
			} catch (Exception exception) {
				System.out.println("No Connection");
				assertTrue(1 == 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPullWeatherAPI(){
		try{
		Wunderground weather = new Wunderground();
		assertTrue(true); 
		}		
		catch(Exception e){ //thrown when weather is null
			assertTrue(false);
		}
	}
	
	@Test
	public void testPullTwitter(){
		//needs private twitter information
		String consumerKeyStr = TwitterInformation.getPublicKey();
		String consumerSecretStr = TwitterInformation.getPrivateKey();
		String accessTokenStr = TwitterInformation.getPublicAccessToken();
		String accessTokenSecretStr = TwitterInformation.getPrivateAccessToken();
		try{
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
		AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);

		twitter.setOAuthAccessToken(accessToken);
		List<Status> statuses = twitter.getMentionsTimeline();		
		assertTrue(true); 
		}		
		catch(Exception e){ //thrown when statuses is null or there isn't a private key.
			assertTrue(false);
		}
	}

}

