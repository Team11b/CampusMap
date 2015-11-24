package WPI.CampusMap.SerializationTest;

import static org.junit.Assert.*;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import java.util.HashMap;
>>>>>>> dev

import org.junit.Test;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Serialization.Serialization;

public class SerializationTest {

	@Test
	public void test() {
		Map tempM = new Map();

		Point oneP = new Point(new Coord(0,0), "", "PointOne", null);
		Point twoP = new Point(new Coord(1,1), "", "PointTwo", null);
		Point threeP = new Point(new Coord(0,1), "", "PointThree", null);
		tempM.addPoint(oneP);
		tempM.addPoint(twoP);
		tempM.addPoint(threeP);
		
		assertTrue(tempM.addEdge(tempM.getPoint("PointOne"), tempM.getPoint("PointTwo")));
		assertTrue(tempM.addEdge(tempM.getPoint("PointThree"), tempM.getPoint("PointOne")));
		assertTrue(tempM.addEdge(tempM.getPoint("PointThree"), tempM.getPoint("PointTwo")));
		
		tempM.setName("test_map");

		Serialization.write(tempM);

		Map temp2 = Serialization.read(tempM.getName());
<<<<<<< HEAD
		System.out.println("Output:\t\t\t" + temp2.getName() + "\t" + temp2.getAllPoints().get(0).getCoord().getX());
		System.out.println("Expected:\t\tnew_map\t1.1");
		
		assertEquals(tempM.getName(),temp2.getName());
		assertEquals(tempM.getPoint("hiya"),temp2.getPoint("hiya"));
		
=======

		assertEquals(tempM.getPoint("PointOne"),temp2.getPoint("PointOne"));
		assertEquals(tempM.getPoint("PointTwo"),temp2.getPoint("PointTwo"));
		assertEquals(tempM.getPoint("PointThree"),temp2.getPoint("PointThree"));
		
		assertEquals(tempM.getPoint("PointOne").getNeighborsP(),
				temp2.getPoint("PointOne").getNeighborsP());
		assertEquals(tempM.getPoint("PointTwo").getNeighborsP(),
				temp2.getPoint("PointTwo").getNeighborsP());
		assertEquals(tempM.getPoint("PointThree").getNeighborsP(),
						temp2.getPoint("PointThree").getNeighborsP());
>>>>>>> dev
	}

}
