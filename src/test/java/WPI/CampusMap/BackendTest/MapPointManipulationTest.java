package WPI.CampusMap.BackendTest;

import static org.junit.Assert.*;

import org.junit.Test;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;

public class MapPointManipulationTest {

	@Test
	public void test() {
		Map testMap= new Map();
		Point oneP = new Point(new Coord(0,0), "", "PointOne", null);
		Point twoP = new Point(new Coord(1,1), "", "PointTwo", null);
		Point threeP = new Point(new Coord(0,1), "", "PointThree", null);
		testMap.addPoint(oneP);
		testMap.addPoint(twoP);
		testMap.addPoint(threeP);
		
		assertEquals(testMap.getAllPoints().size(),3);
		assertTrue(testMap.getAllPoints().contains(oneP));
		assertTrue(testMap.getAllPoints().contains(twoP));
		assertTrue(testMap.getAllPoints().contains(threeP));

		assertTrue(testMap.addEdge(testMap.getPoint("PointOne"), testMap.getPoint("PointTwo")));
		assertTrue(testMap.addEdge(testMap.getPoint("PointThree"), testMap.getPoint("PointOne")));
		assertTrue(testMap.addEdge(testMap.getPoint("PointThree"), testMap.getPoint("PointTwo")));

		assertTrue(oneP.getNeighborsID().contains("PointTwo"));
		assertTrue(oneP.getNeighborsID().contains("PointThree"));
		assertTrue(oneP.getNeighborsP().contains(twoP));
		assertTrue(oneP.getNeighborsP().contains(threeP));
		
		assertTrue(twoP.getNeighborsID().contains("PointOne"));
		assertTrue(twoP.getNeighborsID().contains("PointThree"));
		assertTrue(twoP.getNeighborsP().contains(oneP));
		assertTrue(twoP.getNeighborsP().contains(threeP));
		
		assertTrue(threeP.getNeighborsID().contains("PointTwo"));
		assertTrue(threeP.getNeighborsID().contains("PointOne"));
		assertTrue(threeP.getNeighborsP().contains(twoP));
		assertTrue(threeP.getNeighborsP().contains(oneP));
		
		assertTrue(testMap.removeEdge(testMap.getPoint("PointOne"), testMap.getPoint("PointTwo")));

		assertFalse(oneP.getNeighborsID().contains("PointTwo"));
		assertFalse(oneP.getNeighborsP().contains(twoP));
		assertFalse(twoP.getNeighborsID().contains("PointOne"));
		assertFalse(twoP.getNeighborsP().contains(oneP));
		
		assertTrue(testMap.removePoint("PointThree"));
		
		assertFalse(oneP.getNeighborsID().contains("PointTwo"));
		assertFalse(oneP.getNeighborsID().contains("PointThree"));
		assertFalse(oneP.getNeighborsP().contains(twoP));
		assertFalse(oneP.getNeighborsP().contains(threeP));
		
		assertFalse(twoP.getNeighborsID().contains("PointOne"));
		assertFalse(twoP.getNeighborsID().contains("PointThree"));
		assertFalse(twoP.getNeighborsP().contains(oneP));
		assertFalse(twoP.getNeighborsP().contains(threeP));
		
		assertFalse(threeP.getNeighborsID().contains("PointTwo"));
		assertFalse(threeP.getNeighborsID().contains("PointOne"));
		assertFalse(threeP.getNeighborsP().contains(twoP));
		assertFalse(threeP.getNeighborsP().contains(oneP));

		assertFalse(testMap.removePoint("PointThree"));
	}
	
	@Test
	public void test2() {
		Map testMap = new Map();
		Point p1 = new Point(new Coord(0,0), "t", "55", null);
		assertFalse(testMap.addEdge(p1, p1));
	}

}
