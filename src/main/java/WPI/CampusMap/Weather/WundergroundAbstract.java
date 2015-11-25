package WPI.CampusMap.Weather;

import javax.ws.rs.client.WebTarget;

/**
 * 
 * @author Max Stenke
 */
public abstract class WundergroundAbstract {
	protected WebTarget targetService;
	protected String BASE_URI;

}
