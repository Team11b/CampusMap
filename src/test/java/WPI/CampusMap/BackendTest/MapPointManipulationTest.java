package WPI.CampusMap.BackendTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Map.RealMap;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

public class MapPointManipulationTest {

	@Test
	public void test() {
		ProxyMap testMap = new ProxyMap("testMap");
		RealPoint oneP = new RealPoint(new Coord(0, 0), "", "PointOne", "testMap");
		RealPoint twoP = new RealPoint(new Coord(1, 1), "", "PointTwo", "testMap");
		RealPoint threeP = new RealPoint(new Coord(0, 1), "", "PointThree", "testMap");
		testMap.addPoint(oneP);
		testMap.addPoint(twoP);
		testMap.addPoint(threeP);

		assertEquals(testMap.getAllPoints().size(), 3);
		assertTrue(testMap.getAllPoints().contains(oneP));
		assertTrue(testMap.getAllPoints().contains(twoP));
		assertTrue(testMap.getAllPoints().contains(threeP));

		assertTrue(testMap.addEdge(testMap.getPoint("PointOne"), testMap.getPoint("PointTwo")));
		assertTrue(testMap.addEdge(testMap.getPoint("PointThree"), testMap.getPoint("PointOne")));
		assertTrue(testMap.addEdge(testMap.getPoint("PointThree"), testMap.getPoint("PointTwo")));

		assertTrue(oneP.getNeighborsP().contains(twoP));
		assertTrue(oneP.getNeighborsP().contains(threeP));

		assertTrue(twoP.getNeighborsP().contains(oneP));
		assertTrue(twoP.getNeighborsP().contains(threeP));

		assertTrue(threeP.getNeighborsP().contains(twoP));
		assertTrue(threeP.getNeighborsP().contains(oneP));

		assertTrue(testMap.removeEdge(testMap.getPoint("PointOne"), testMap.getPoint("PointTwo")));

		assertFalse(oneP.getNeighborsP().contains(twoP));
		assertFalse(twoP.getNeighborsP().contains(oneP));

		assertTrue(testMap.removePoint("PointThree"));

		assertFalse(oneP.getNeighborsP().contains(twoP));
		assertFalse(oneP.getNeighborsP().contains(threeP));

		assertFalse(twoP.getNeighborsP().contains(oneP));
		assertFalse(twoP.getNeighborsP().contains(threeP));

		assertFalse(threeP.getNeighborsP().contains(twoP));
		assertFalse(threeP.getNeighborsP().contains(oneP));

		assertTrue(testMap.removePoint(oneP));
	}

	@Test
	public void test2() {
		RealMap testMap = new RealMap("testMap");
		RealPoint p1 = new RealPoint(new Coord(0, 0), "t", "55", "testMap");
		assertFalse(testMap.addEdge(p1, p1));
	}

}
