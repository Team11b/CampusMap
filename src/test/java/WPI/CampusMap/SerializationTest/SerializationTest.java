package WPI.CampusMap.SerializationTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Serialization.Serialization;

public class SerializationTest {

	@Test
	public void test() {
		Map tempM = new Map();
		tempM.setName("test_map");
		ArrayList<Point> tempP = new ArrayList<Point>();
		tempP.add(new Point(new Coord((float) 1.1, 0), "hiya", "id", null));
		tempM.setAllPoints(tempP);

		Serialization.write(tempM);

		Map temp2 = Serialization.read(tempM.getName());
		System.out.println("Output:\t\t\t" + temp2.getName() + "\t" + temp2.getAllPoints().get(0).getCoord().getX());
		System.out.println("Expected:\t\tnew_map\t1.1");
		
		assertEquals(tempM.getName(),temp2.getName());
		assertEquals(tempM.getPoint("hiya"),temp2.getPoint("hiya"));
		
	}

}
