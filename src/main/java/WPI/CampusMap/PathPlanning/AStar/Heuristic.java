package WPI.CampusMap.PathPlanning.AStar;

public class Heuristic {
	// see http://www.nws.noaa.gov/os/guide/Section9.pdf
	public static final int breezyThreshold = 15;
	public static final int windyThreshold = 25;

	public static final double breezyCost = -2.5;
	public static final double windyCost = -5.0;

	public static final double sunnyCost = 5.0;
	public static final double percipCost = 100.0;

	public static final double rfDegCost = 0.5;

	public static final double wPosDegCost = 1.0;
	public static final double wNegDegCost = -1.0;

	public static final double sPosDegCost = -1.0;
	public static final double sNegDegCost = 1.0;

	// see
	// http://discovernewengland.org/about-new-england/new-england-usa-quick-facts-0
	public static final int[] sAvg = { 80, 85 };
	public static final int[] fAvg = { 45, 50 };
	public static final int[] wAvg = { 25, 25 };
	public static final int[] rAvg = { 40, 60 };

	public Heuristic() {

	}

	private static boolean isSunny(String report) {
		return false;
	}
	
	private static boolean isBreezy(String report) {
		return false;
	}
	
	private static boolean isWindy(String report) {
		return false;
	}
	
	private static boolean isPercip(String report) {
		return false;
	}
	
//	sunny: + 2.5pts
//	windy: - 2.5pts
//	rainy: INSIDE
//
//	temp::
//	based on average temps listed here: http://discovernewengland.org/about-new-england/new-england-usa-quick-facts-0
//
//	spring/fall:
//	within average: 0 pts
//	for each deg above avg: +0.5 pts
//	for each deg below avg: -0.5 pts
//
//	winter:
//	within average: 0 pts
//	for each deg above avg: +1   pts
//	for each deg below avg: -1   pts
//
//	summer:
//	within average: 0 pts
//	for each deg above avg: -1   pts
//	for each deg below avg: +1   pts
	public double getWeatherScore() {
		return 0.0;
	}

}
