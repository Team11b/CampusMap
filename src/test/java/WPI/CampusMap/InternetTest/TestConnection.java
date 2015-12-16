package WPI.CampusMap.InternetTest;

import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.Wunderground;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TestConnection {
	@Rule
	 public final ExpectedException exception = ExpectedException.none();
	
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
		exception.expect(UnsupportedOperationException.class);
		//needs private twitter information
		String consumerKeyStr = TwitterInformation.getPublicKey();
		String consumerSecretStr = TwitterInformation.getPrivateKey();
		String accessTokenStr = TwitterInformation.getPublicAccessToken();
		String accessTokenSecretStr = TwitterInformation.getPrivateAccessToken();
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
		AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);

		twitter.setOAuthAccessToken(accessToken);
		try {
			List<Status> statuses = twitter.getMentionsTimeline();
			assertTrue(true);
		} catch (TwitterException e) {			
			e.printStackTrace();
			assertTrue(false);
		}		
		
	}
	

}

