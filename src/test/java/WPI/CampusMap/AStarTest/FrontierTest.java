package WPI.CampusMap.AStarTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.AStar.Frontier;
import WPI.CampusMap.PathPlanning.AStar.Node;

public class FrontierTest {
	public static Frontier testing;

	public static Node alpha;
	public static Node beta;
	public static Node gamma;
	public static Node delta;
	public static Node eta;
	public static Node iota;

	@Ignore
	@BeforeClass
	public static void beforeClass() {
		testing = new Frontier(Frontier.stdNodeComp);

		alpha = new Node(new Point(new Coord(0.0, 0.0), Point.HALLWAY, "alpha"), null);
		alpha.setCurrentScore(9.0);
		beta = new Node(new Point(new Coord(1.0, 1.0), Point.HALLWAY, "beta"), null);
		beta.setCurrentScore(3.0);
		gamma = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "gamma"), null);
		gamma.setCurrentScore(9.0);
		
		delta = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "delta"), null);
		delta.setCurrentScore(5.0);
		
		eta = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "eta"), null);
		eta.setCurrentScore(10.0);
		
		iota = new Node(new Point(new Coord(5.0, 5.0), Point.HALLWAY, "iota"), null);
	}
	
	@Before
	public void before() {
		testing = new Frontier(Frontier.stdNodeComp);

		alpha = new Node(new Point(new Coord(0.0, 0.0), Point.HALLWAY, "alpha"), null);
		alpha.setCurrentScore(9.0);
		beta = new Node(new Point(new Coord(1.0, 1.0), Point.HALLWAY, "beta"), null);
		beta.setCurrentScore(3.0);
		gamma = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "gamma"), null);
		gamma.setCurrentScore(9.0);
		
		delta = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "delta"), null);
		delta.setCurrentScore(5.0);
		
		eta = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "eta"), null);
		eta.setCurrentScore(10.0);
		
		iota = new Node(new Point(new Coord(5.0, 5.0), Point.HALLWAY, "iota"), null);
		
		testing.add(beta);

		testing.add(alpha);
		
		testing.add(gamma);
		
		testing.add(eta);
	}

	@Ignore
	@Test
	public void testAdd() {
		testing.add(beta);

		testing.add(alpha);
		
		testing.add(gamma);
		
		testing.add(eta);
		
		assertTrue(testing.size() == 4);
	}
	
	@Test
	public void testRemove() {
		
		assertTrue(testing.getNext().getPoint().getId().equals("beta"));
		
		assertTrue(testing.size() == 3);
	}
	
	@Test
	public void testContains() {
		assertTrue(testing.contains(eta));
		assertFalse(testing.contains(iota));
	}
	
	@Test
	public void testBetter() {
		assertTrue(testing.isBetter(delta));
		assertTrue(testing.size() == 4);
		
		assertFalse(testing.isBetter(eta));
		assertTrue(testing.size() == 4);
	}

}
