package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather;

import javax.swing.JOptionPane;
import javax.ws.rs.ProcessingException;
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
	private boolean hasWeather;
	private static final String error = "Weather cannot be recieved.";

	public Wunderground() {
		BASE_URI = "http://api.wunderground.com/api/2901b67aa5967692/conditions/q/MA/Worcester.json";

		Client client = ClientBuilder.newClient();
		targetService = client.target(BASE_URI);

		Response results = null;

		try {
			results = targetService.request().get();
		} catch (ProcessingException e) {
			hasWeather = false;
			JOptionPane.showMessageDialog(null, Wunderground.error, "ERROR", JOptionPane.ERROR_MESSAGE);
			System.out.println(Wunderground.error);
		}

		if (results != null) {
			String responseAsString = results.readEntity(String.class);
			Gson gson = new Gson();
			resp = gson.fromJson(responseAsString, ResponseContainer.class);
			hasWeather = true;
		}
	}

	public double getTempC() {
		if (!(hasWeather)) {
			return 0.0;
		}
		return resp.getCurrent_observation().getTempC();
	}

	public double getTempF() {
		if (!(hasWeather)) {
			return 0.0;
		}
		return resp.getCurrent_observation().getTempF();
	}

	public String getWeather() {
		if (!(hasWeather)) {
			return Wunderground.error;
		}
		return resp.getCurrent_observation().getWeather();
	}

	public double getWindMPH() {
		if (!(hasWeather)) {
			return 0.0;
		}
		return resp.getCurrent_observation().getWindMPH();
	}

	public String toString() {
		return "" + this.getWeather() + " @ " + this.getTempF() + " degF & " + this.getWindMPH() + " MPH";
	}

	public boolean hasWeather() {
		return this.hasWeather;
	}
}
