package WPI.CampusMap.AStarTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Point;

public class AStarTest4 {
	static Coord cOne, cTwo, cThree, cFour;
	static Point one, two, three;
	
	@BeforeClass
	public static void beforeClass(){
		cOne = new Coord(1, 1);
		cTwo = new Coord(1, 1);
		cThree = new Coord(2, 1);
		cFour = new Coord(1, 2);

		one = new Point(cOne, Point.DOOR, "alpha");
		two = new Point(cTwo, Point.ELEVATOR, "beta");
		three = new Point(cThree, Point.STAIRS, "gamma");
	}
	
	@Test
	public void testPointEquals(){
		
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
