package WPI.CampusMap.BackendTest;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;

public class CoordTest {
	private Coord coord1, coord2, coord3, equalsCoord1, equalsCoord2;

	@Before
	public void initialize() {
		coord1 = new Coord(1.0, 1.0);
		coord2 = new Coord(1.0, 5.0);
		coord3 = new Coord(3.0, 5.0);
		equalsCoord1 = new Coord(1.0, 1.0);
		equalsCoord2 = new Coord(1.0, 5.0);
	}

	@Test
	public void testIfDistanceGivesCorrectValues() {
		double dist1 = coord1.distance(coord2);
		double dist2 = coord1.distance(coord3);

		assertTrue(dist1 == 4.0);
		assertTrue(dist2 == 4.4721360206604);
	}

	@Test
	public void testIfEqualsMethodComparesCorrect() {
		boolean comp1 = coord1.equals(equalsCoord1);
		boolean comp2 = coord2.equals(coord3);
		boolean comp3 = coord2.equals(equalsCoord2);

		assertTrue(comp1);
		assertFalse(comp2);
		assertTrue(comp3);
	}
}
