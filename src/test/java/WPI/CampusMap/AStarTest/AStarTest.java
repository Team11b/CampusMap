package WPI.CampusMap.AStarTest;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Point;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AStarTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AStarTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AStarTest.class);
	}

	public void testPointEquals() {
		Coord cOne = new Coord(1, 1);
		Coord cTwo = new Coord(1, 1);
		Coord cThree = new Coord(2, 1);

		Point one = new Point(cOne, Point.DOOR, "alpha");
		Point two = new Point(cTwo, Point.ELEVATOR, "beta");
		Point three = new Point(cThree, Point.STAIRS, "gamma");

		assertTrue(one.equals(two));

		assertFalse(one.equals(three));
	}

	public void testCoordEquals() {
		Coord cOne = new Coord(1, 1);
		Coord cTwo = new Coord(1, 1);
		Coord cThree = new Coord(2, 1);
		Coord cFour = new Coord(1, 2);

		assertTrue(cOne.equals(cTwo));

		assertFalse(cOne.equals(cThree));
		
		assertFalse(cOne.equals(cFour));
	}
}
