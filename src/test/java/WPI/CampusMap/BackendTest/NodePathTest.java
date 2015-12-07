package WPI.CampusMap.BackendTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.Path;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;

@Ignore
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

		one = new Point(cOne, Point.OUT_DOOR, "alpha", "");
		two = new Point(cTwo, Point.ELEVATOR, "beta", "");
		three = new Point(cThree, Point.STAIRS, "gamma", "");
		three = new Point(cThree, Point.STAIRS, "gamma", "");

		nOne = new Node(one, null, one);
		nTwo = new Node(two, nOne, one);
		nThree = new Node(three, nTwo, one);

		aPath = new Path(1);
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

		System.out.println(aPath.getPath());
		assertTrue(nOne.getPoint().equals(aPath.getPath().get(0).getPoint()));

	}

	@Test
	public void testPointEquals() {

		assertTrue(one.equals(one));
		assertFalse(one.equals(two));
		assertFalse(one.equals(three));
	}

	@Test
	public void testCoordEquals() {

		assertTrue(cOne.equals(cTwo));

		assertFalse(cOne.equals(cThree));

		assertFalse(cOne.equals(cFour));
	}

}
