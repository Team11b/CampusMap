package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

/**
 * 
 * @author Max Stenke
 */
public class Wunderground extends WundergroundAbstract {
	private ResponseContainer resp;

	public Wunderground() {
		BASE_URI = "http://api.wunderground.com/api/2901b67aa5967692/conditions/q/MA/Worcester.json";
		Client client = ClientBuilder.newClient();
		targetService = client.target(BASE_URI);
		Response results = targetService.request().get();
		String responseAsString = results.readEntity(String.class);
		Gson gson = new Gson();
		resp = gson.fromJson(responseAsString, ResponseContainer.class);
	}

	public double getTempC() {
		return resp.getCurrent_observation().getTempC();
	}

	public double getTempF() {
		return resp.getCurrent_observation().getTempF();
	}

	public String getWeather() {
		return resp.getCurrent_observation().getWeather();
	}

	public double getWindMPH() {
		return resp.getCurrent_observation().getWindMPH();
	}
	
	public String toString() {
		return "" + this.getWeather() + " @ " + this.getTempF() + " degF & " + this.getWindMPH() + " MPH";
	}
}
