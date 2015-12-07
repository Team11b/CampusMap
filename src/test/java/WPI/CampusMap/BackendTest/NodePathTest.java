package WPI.CampusMap.BackendTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.PathPlanning.Node;
import WPI.CampusMap.Backend.PathPlanning.Path;


public class NodePathTest {
	static Coord cOne, cTwo, cThree, cFour;
	static RealPoint one, two, three;
	static Node nOne, nTwo, nThree;
	static Path aPath;

	@BeforeClass
	public static void beforeClass() {
		cOne = new Coord(1, 1);
		cTwo = new Coord(1, 1);
		cThree = new Coord(2, 1);
		cFour = new Coord(1, 2);

		one = new RealPoint(cOne, RealPoint.OUT_DOOR, "alpha", "");
		two = new RealPoint(cTwo, RealPoint.ELEVATOR, "beta", "");
		three = new RealPoint(cThree, RealPoint.STAIRS, "gamma", "");
		three = new RealPoint(cThree, RealPoint.STAIRS, "gamma", "");

		nOne = new Node(one,null,0);
		nTwo = new Node(two,nOne,0);
		nThree = new Node(three,nTwo,0);
		
		aPath = new Path(nOne);
	}

	@Test
	public void testCoordDist() {
		assertTrue(cOne.distance(cTwo) == 0);
		assertTrue(cOne.distance(cThree) == 1);
		assertTrue(cOne.distance(cFour) == 1);
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
