package WPI.CampusMap.Backend.Core.Weather;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

/**
 * 
 * @author Max Stenke
 */
public class Wunderground extends WundergroundAbstract {
	ResponseContainer resp;

	public Wunderground() {
		BASE_URI = "http://api.wunderground.com/api/2901b67aa5967692/conditions/q/MA/Worcester.json";
		Client client = ClientBuilder.newClient();
		targetService = client.target(BASE_URI);
		Response results = targetService.request().get();
		String responseAsString = results.readEntity(String.class);
		Gson gson = new Gson();
		resp = gson.fromJson(responseAsString, ResponseContainer.class);

		// System.out.println(responseAsString);
	}

	public double getTempC() {
		return resp.current_observation.getTempC();
	}

	public double getTempF() {
		return resp.current_observation.getTempF();
	}

	public String getWeather() {
		return resp.current_observation.getWeather();
	}

	public double getWindMPH() {
		return resp.current_observation.getWindMPH();
	}
}
