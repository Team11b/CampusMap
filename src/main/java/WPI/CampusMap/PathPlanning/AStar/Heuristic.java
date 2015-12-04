package WPI.CampusMap.PathPlanning.AStar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import WPI.CampusMap.Weather.Wunderground;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Heuristic {

	private static boolean useWeather = true;

	// see http://www.nws.noaa.gov/os/guide/Section9.pdf
	private static final int breezyThreshold = 15;
	private static final int windyThreshold = 25;

	private static final double breezyCost = -2.5;
	private static final double windyCost = -5.0;

	private static final double sunnyCost = 5.0;
	private static final double percipCost = -100.0;

	private static final double rfDegCost = 0.5;
	private static double rfPosDegCost = rfDegCost;
	private static double rfNegDegCost = rfDegCost;

	private static final double wPosDegCost = 1.0;
	private static final double wNegDegCost = -1.0;

	private static final double sPosDegCost = -1.0;
	private static final double sNegDegCost = 1.0;

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

	public Heuristic() {

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
		String usable = Heuristic.removeWhitespace(report).toLowerCase();

		return (usable.contains("sunny"));
	}

	private static boolean isBreezy(double wind) {
		return ((wind >= breezyThreshold) && (wind < windyThreshold));
	}

	private static boolean isWindy(double wind) {
		return (wind >= windyThreshold);
	}

	private static boolean isPercip(String report) {
		String usable = Heuristic.removeWhitespace(report).toLowerCase();

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
		if (Heuristic.isSunny(Heuristic.getWeather().getWeather())) {
			score += Heuristic.sunnyCost;
		}

		// WINDY
		if (Heuristic.isBreezy(Heuristic.getWeather().getWindMPH())) {
			score += Heuristic.breezyCost;
		} else if (Heuristic.isWindy(Heuristic.getWeather().getWindMPH())) {
			score += Heuristic.windyCost;
		}

		// PERCIP
		if (Heuristic.isPercip(Heuristic.getWeather().getWeather())) {
			score += Heuristic.percipCost;
		} else {
			// TEMP
			switch (Heuristic.month) {
			case (Calendar.DECEMBER):
				score += Heuristic.wTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.JANUARY):
				score += Heuristic.wTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.FEBRUARY):
				score += Heuristic.wTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.MARCH):
				score += Heuristic.rTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.APRIL):
				score += Heuristic.rTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.MAY):
				score += Heuristic.rTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.JUNE):
				score += Heuristic.sTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.JULY):
				score += Heuristic.sTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.AUGUST):
				score += Heuristic.sTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.SEPTEMBER):
				score += Heuristic.fTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.OCTOBER):
				score += Heuristic.fTemp(Heuristic.getWeather().getTempF());
				break;
			case (Calendar.NOVEMBER):
				score += Heuristic.fTemp(Heuristic.getWeather().getTempF());
				break;
			default:
				score += 0;
				break;
			}
		}

		return score;
	}

	private static double fTemp(double tempF) {
		if ((tempF >= Heuristic.fAvg[Heuristic.LOW]) && (tempF <= Heuristic.fAvg[Heuristic.HIGH])) {
			return 0;
		} else if (tempF < Heuristic.fAvg[Heuristic.LOW]) {
			return Math.abs(Heuristic.fAvg[Heuristic.LOW] - tempF) * Heuristic.rfPosDegCost;
		} else {
			return Math.abs(tempF - Heuristic.fAvg[Heuristic.HIGH]) * Heuristic.rfNegDegCost;
		}
	}

	private static double sTemp(double tempF) {
		if ((tempF >= Heuristic.sAvg[Heuristic.LOW]) && (tempF <= Heuristic.sAvg[Heuristic.HIGH])) {
			return 0;
		} else if (tempF < Heuristic.sAvg[Heuristic.LOW]) {
			return Math.abs(Heuristic.sAvg[Heuristic.LOW] - tempF) * Heuristic.sPosDegCost;
		} else {
			return Math.abs(tempF - Heuristic.sAvg[Heuristic.HIGH]) * Heuristic.sNegDegCost;
		}
	}

	private static double rTemp(double tempF) {
		if ((tempF >= Heuristic.rAvg[Heuristic.LOW]) && (tempF <= Heuristic.rAvg[Heuristic.HIGH])) {
			return 0;
		} else if (tempF < Heuristic.rAvg[Heuristic.LOW]) {
			return Math.abs(Heuristic.rAvg[Heuristic.LOW] - tempF) * Heuristic.rfPosDegCost;
		} else {
			return Math.abs(tempF - Heuristic.rAvg[Heuristic.HIGH]) * Heuristic.rfNegDegCost;
		}
	}

	private static double wTemp(double tempF) {
		if ((tempF >= Heuristic.wAvg[Heuristic.LOW]) && (tempF <= Heuristic.wAvg[Heuristic.HIGH])) {
			return 0;
		} else if (tempF < Heuristic.wAvg[Heuristic.LOW]) {
			return Math.abs(Heuristic.wAvg[Heuristic.LOW] - tempF) * Heuristic.wNegDegCost;
		} else {
			return Math.abs(tempF - Heuristic.wAvg[Heuristic.HIGH]) * Heuristic.wPosDegCost;
		}
	}

}
