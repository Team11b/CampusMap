package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather;

/**
 * 
 * @author Max Stenke
 */
public class ResponseWun {
	private String weather;
	private double temp_c;
	private double temp_f;
	private double wind_mph;

	public double getTempC() {
		return this.temp_c;
	}

	public double getTempF() {
		return this.temp_f;
	}

	public String getWeather() {
		return this.weather;
	}

	public double getWindMPH() {
		return this.wind_mph;
	}

}
