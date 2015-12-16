package WPI.CampusMap.InternetTest;

import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.Wunderground;

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
	
	@SuppressWarnings("unused")
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

}

