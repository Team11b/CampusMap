package WPI.CampusMap.BackendTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;

public class NodePathTest {
	static Coord cOne, cTwo, cThree, cFour;
	static Point one, two, three;
	static Node nOne, nTwo, nThree;
	static Path aPath;

	@BeforeClass
	public static void beforeClass() {
		cOne = new Coord(1, 1);
		cTwo = new Coord(1, 1);
		cThree = new Coord(2, 1);
		cFour = new Coord(1, 2);

		one = new Point(cOne, Point.OUT_DOOR, "alpha", null);
		two = new Point(cTwo, Point.ELEVATOR, "beta", null);
		three = new Point(cThree, Point.STAIRS, "gamma", null);

		nOne = new Node(one, null);
		nTwo = new Node(two, nOne);
		nThree = new Node(three, nTwo);

		aPath = new Path();
		aPath.addNode(nThree);
		aPath.addNode(nTwo);
		aPath.addNode(nOne);
	}

	@Test
	public void testCoordDist() {
		assertTrue(cOne.distance(cTwo) == 0);
		assertTrue(cOne.distance(cThree) == 1);
		assertTrue(cOne.distance(cFour) == 1);
	}

	@Test
	public void testPathReverse() {
		aPath.reverse();

		assertTrue(nOne.getPoint().equals(aPath.getPath().get(0).getPoint()));

	}

	@Test
	public void testPointEquals() {

		assertTrue(one.equals(two));

		assertFalse(one.equals(three));
	}

	@Test
	public void testCoordEquals() {

		assertTrue(cOne.equals(cTwo));

		assertFalse(cOne.equals(cThree));

		assertFalse(cOne.equals(cFour));
	}

}
