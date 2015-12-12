package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class WeatherAnalysis {

	private static boolean useWeather = true;

	// see http://www.nws.noaa.gov/os/guide/Section9.pdf
	private static final int breezyThreshold = 15;
	private static final int windyThreshold = 25;

	private static final double breezyCost = 2.5;
	private static final double windyCost = 5.0;

	private static final double sunnyCost = -5.0;
	private static final double percipCost = 50.0;

	private static final double rfDegCost = 0.5;
	private static double rfPosDegCost = rfDegCost;
	private static double rfNegDegCost = rfDegCost;

	private static final double wPosDegCost = -1.0;
	private static final double wNegDegCost = 1.0;

	private static final double sPosDegCost = 1.0;
	private static final double sNegDegCost = -1.0;

	// see
	// http://discovernewengland.org/about-new-england/new-england-usa-quick-facts-0
	private static final int[] sAvg = { 80, 85 };
	private static final int[] fAvg = { 45, 50 };
	private static final int[] wAvg = { 25, 25 };
	private static final int[] rAvg = { 40, 60 };
	private static final int LOW = 0;
	private static final int HIGH = 1;

	private static final Wunderground weather = new Wunderground();

	// month
	private static final GregorianCalendar gc = new GregorianCalendar();
	private static final int month = gc.get(GregorianCalendar.MONTH);

	public WeatherAnalysis() {

	}

	public static boolean isUsingWeather() {
		return true;
	}

	/**
	 * @return the weather
	 */
	public static Wunderground getWeather() {
		return weather;
	}

	/**
	 * @return the month
	 */
	public static int getMonth() {
		return month;
	}

	private static boolean isSunny(String report) {
		String usable = WeatherAnalysis.removeWhitespace(report).toLowerCase();

		return (usable.contains("sunny"));
	}

	private static boolean isBreezy(double wind) {
		return ((wind >= breezyThreshold) && (wind < windyThreshold));
	}

	private static boolean isWindy(double wind) {
		return (wind >= windyThreshold);
	}

	private static boolean isPercip(String report) {
		String usable = WeatherAnalysis.removeWhitespace(report).toLowerCase();

		return ((usable.contains("rain")) || (usable.contains("snow")) || (usable.contains("storm"))
				|| (usable.contains("hail")));
	}

	/**
	 * Removes whitespace from the input string and returns a copy The input
	 * string is not modified
	 * 
	 * @param input
	 * @return input without whitespace
	 */
	private static String removeWhitespace(String input) {
		return input.replaceAll("\\s", "");
	}

	/**
	 * Calculates a heuristic modifier based upon the weather using the
	 * following algorithm:
	 * 
	 * sunny: + 2.5 pts windy: - 2.5 pts drizzle: 0 pts percip: INSIDE
	 * 
	 * temp:: based on average temps listed here:
	 * http://discovernewengland.org/about-new-england/new-england-usa-quick-
	 * facts-0
	 * 
	 * spring/fall: within average: 0 pts for each deg above avg: +0.5 pts for
	 * each deg below avg: -0.5 pts
	 * 
	 * winter: within average: 0 pts for each deg above avg: +1 pts for each deg
	 * below avg: -1 pts
	 * 
	 * summer: within average: 0 pts for each deg above avg: -1 pts for each deg
	 * below avg: +1 pts
	 * 
	 * A positive score favors outdoors A negative score favors indoors
	 * 
	 * @return modifier score
	 */
	public static double getWeatherScore() {
		if (!useWeather) {
			return 0.0;
		}

		double score = 0.0;

		// SUNNY
		if (WeatherAnalysis.isSunny(WeatherAnalysis.getWeather().getWeather())) {
			score += WeatherAnalysis.sunnyCost;
		}

		// WINDY
		if (WeatherAnalysis.isBreezy(WeatherAnalysis.getWeather().getWindMPH())) {
			score += WeatherAnalysis.breezyCost;
		} else if (WeatherAnalysis.isWindy(WeatherAnalysis.getWeather().getWindMPH())) {
			score += WeatherAnalysis.windyCost;
		}

		// PERCIP
		if (WeatherAnalysis.isPercip(WeatherAnalysis.getWeather().getWeather())) {
			score += WeatherAnalysis.percipCost;
		} else {
			// TEMP
			switch (WeatherAnalysis.month) {
			case (Calendar.DECEMBER):
				score += WeatherAnalysis.wTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.JANUARY):
				score += WeatherAnalysis.wTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.FEBRUARY):
				score += WeatherAnalysis.wTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.MARCH):
				score += WeatherAnalysis.rTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.APRIL):
				score += WeatherAnalysis.rTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.MAY):
				score += WeatherAnalysis.rTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.JUNE):
				score += WeatherAnalysis.sTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.JULY):
				score += WeatherAnalysis.sTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.AUGUST):
				score += WeatherAnalysis.sTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.SEPTEMBER):
				score += WeatherAnalysis.fTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.OCTOBER):
				score += WeatherAnalysis.fTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			case (Calendar.NOVEMBER):
				score += WeatherAnalysis.fTemp(WeatherAnalysis.getWeather().getTempF());
				break;
			default:
				score += 0;
				break;
			}
		}

		return score;
	}

	private static double fTemp(double tempF) {
		if ((tempF >= WeatherAnalysis.fAvg[WeatherAnalysis.LOW]) && (tempF <= WeatherAnalysis.fAvg[WeatherAnalysis.HIGH])) {
			return 0;
		} else if (tempF < WeatherAnalysis.fAvg[WeatherAnalysis.LOW]) {
			return Math.abs(WeatherAnalysis.fAvg[WeatherAnalysis.LOW] - tempF) * WeatherAnalysis.rfPosDegCost;
		} else {
			return Math.abs(tempF - WeatherAnalysis.fAvg[WeatherAnalysis.HIGH]) * WeatherAnalysis.rfNegDegCost;
		}
	}

	private static double sTemp(double tempF) {
		if ((tempF >= WeatherAnalysis.sAvg[WeatherAnalysis.LOW]) && (tempF <= WeatherAnalysis.sAvg[WeatherAnalysis.HIGH])) {
			return 0;
		} else if (tempF < WeatherAnalysis.sAvg[WeatherAnalysis.LOW]) {
			return Math.abs(WeatherAnalysis.sAvg[WeatherAnalysis.LOW] - tempF) * WeatherAnalysis.sPosDegCost;
		} else {
			return Math.abs(tempF - WeatherAnalysis.sAvg[WeatherAnalysis.HIGH]) * WeatherAnalysis.sNegDegCost;
		}
	}

	private static double rTemp(double tempF) {
		if ((tempF >= WeatherAnalysis.rAvg[WeatherAnalysis.LOW]) && (tempF <= WeatherAnalysis.rAvg[WeatherAnalysis.HIGH])) {
			return 0;
		} else if (tempF < WeatherAnalysis.rAvg[WeatherAnalysis.LOW]) {
			return Math.abs(WeatherAnalysis.rAvg[WeatherAnalysis.LOW] - tempF) * WeatherAnalysis.rfPosDegCost;
		} else {
			return Math.abs(tempF - WeatherAnalysis.rAvg[WeatherAnalysis.HIGH]) * WeatherAnalysis.rfNegDegCost;
		}
	}

	private static double wTemp(double tempF) {
		if ((tempF >= WeatherAnalysis.wAvg[WeatherAnalysis.LOW]) && (tempF <= WeatherAnalysis.wAvg[WeatherAnalysis.HIGH])) {
			return 0;
		} else if (tempF < WeatherAnalysis.wAvg[WeatherAnalysis.LOW]) {
			return Math.abs(WeatherAnalysis.wAvg[WeatherAnalysis.LOW] - tempF) * WeatherAnalysis.wNegDegCost;
		} else {
			return Math.abs(tempF - WeatherAnalysis.wAvg[WeatherAnalysis.HIGH]) * WeatherAnalysis.wPosDegCost;
		}
	}

}
