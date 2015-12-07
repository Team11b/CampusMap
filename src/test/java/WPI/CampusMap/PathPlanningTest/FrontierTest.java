package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Map.RealMap;
import WPI.CampusMap.Backend.PathPlanning.Node;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar.Frontier;

public class FrontierTest {
	public static Frontier testing;
	public static RealMap testMap;

	public static Node alpha;
	public static Node beta;
	public static Node gamma;
	public static Node delta;
	public static Node eta;
	public static Node iota;

//	@BeforeClass
//	public static void beforeClass() {
//		AllMaps.getInstance().clearAllMaps();
//		testing = new Frontier();
//		testMap = new RealMap("testMap");
//		AllMaps.getInstance().addMap(testMap);
//
//		alpha = new Node(new RealPoint(new Coord(0.0, 0.0), RealPoint.HALLWAY, "alpha", "testMap"));
//		alpha.setAccumulatedDistance(9.0);
//		beta = new Node(new RealPoint(new Coord(1.0, 1.0), RealPoint.HALLWAY, "beta", "testMap"));
//		beta.setAccumulatedDistance(3.0);
//		gamma = new Node(new RealPoint(new Coord(2.0, 2.0), RealPoint.HALLWAY, "gamma", "testMap"));
//		gamma.setAccumulatedDistance(9.0);
//		
//		delta = new Node(new RealPoint(new Coord(2.0, 2.0), RealPoint.HALLWAY, "delta", "testMap"));
//		delta.setAccumulatedDistance(5.0);
//		
//		eta = new Node(new RealPoint(new Coord(2.0, 2.0), RealPoint.HALLWAY, "eta", "testMap"));
//		eta.setAccumulatedDistance(10.0);
//		
//		iota = new Node(new RealPoint(new Coord(5.0, 5.0), RealPoint.HALLWAY, "iota", "testMap"));
//	}
	
//	@Before
//	public void before() {
//		testing = new Frontier();
//
//		alpha = new Node(new RealPoint(new Coord(0.0, 0.0), RealPoint.HALLWAY, "alpha", null), null);
//		alpha.setCurrentScore(9.0);
//		beta = new Node(new RealPoint(new Coord(1.0, 1.0), RealPoint.HALLWAY, "beta", null), null);
//		beta.setCurrentScore(3.0);
//		gamma = new Node(new RealPoint(new Coord(2.0, 2.0), RealPoint.HALLWAY, "gamma", null), null);
//		gamma.setCurrentScore(9.0);
//		
//		delta = new Node(new RealPoint(new Coord(2.0, 2.0), RealPoint.HALLWAY, "delta", null), null);
//		delta.setCurrentScore(5.0);
//		
//		eta = new Node(new RealPoint(new Coord(2.0, 2.0), RealPoint.HALLWAY, "eta", null), null);
//		eta.setCurrentScore(10.0);
//		
//		iota = new Node(new RealPoint(new Coord(5.0, 5.0), RealPoint.HALLWAY, "iota", null), null);
//		
//		testing.addToFrontier(beta);
//
//		testing.addToFrontier(alpha);
//		
//		testing.addToFrontier(gamma);
//		
//		testing.addToFrontier(eta);
//	}

//	@Test
//	public void testAdd() {
//		testing.add(beta);
//
//		testing.add(alpha);
//		
//		testing.add(gamma);
//		
//		testing.add(eta);
//		
//		assertTrue(testing.size() == 4);
//	}
	
	@Ignore
	@Test
	public void testRemove() {
		
		assertTrue(testing.getNext().getPoint().getId().equals("beta"));
		
		assertTrue(testing.size() == 3);
	}

	@Ignore
	@Test
	public void testContains() {
		assertTrue(testing.contains(eta));
		assertFalse(testing.contains(iota));
	}

	@Ignore
	@Test
	public void testBetter() {
//		assertTrue(testing.isBetter(delta));
		assertTrue(testing.size() == 4);
		
//		assertFalse(testing.isBetter(eta));
		assertTrue(testing.size() == 4);
	}

}