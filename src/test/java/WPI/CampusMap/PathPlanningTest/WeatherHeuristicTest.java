package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.WeatherAnalysis;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.Wunderground;
import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;

@Ignore
public class WeatherHeuristicTest {
	public static Wunderground weather;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		weather = new Wunderground();
		System.out.println(weather);
	}

	// When testing Weather based heuristic, run the following test once. Calculate what the heuristic
	// should be. Replace that value in the tests. Remove the @Ignore. Add the @Ignore back after you are done.
	@Ignore
	@Test
	public void weatherScore() {
		System.out.println(weather);
		assertEquals(WeatherAnalysis.getWeatherScore(), -100.0, 0.1);
	}
	
	@Ignore
	@Test
	public void heuristicScore() {
		Node anode = new Node(new RealPoint(new Coord(0,0), RealPoint.HALLWAY, "1", "Atwater_Kent-0"), null, null);
		System.out.println(anode.calcHeuristic(new RealPoint(new Coord(0,0), RealPoint.HALLWAY, "1", "Fuller_Labs-0")));
		assertEquals(anode.calcHeuristic(new RealPoint(new Coord(0,0), RealPoint.HALLWAY, "1", "Fuller_Labs-0")), -100.0, 0.1);
	}
	
	@Ignore
	@Test
	public void heuristicScore2() {
		Node anode = new Node(new RealPoint(new Coord(0,0), RealPoint.HALLWAY, "1", "CampusMap"), null, null);
		System.out.println(anode.calcHeuristic(new RealPoint(new Coord(0,0), RealPoint.HALLWAY, "1", "Fuller_Labs-0")));
		assertEquals(anode.calcHeuristic(new RealPoint(new Coord(0,0), RealPoint.HALLWAY, "1", "Fuller_Labs-0")), -100.0, 0.1);
	}

}
