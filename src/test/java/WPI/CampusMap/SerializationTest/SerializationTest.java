package WPI.CampusMap.SerializationTest;

import java.util.HashMap;

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

		HashMap<String, Point> tempP = new HashMap<String, Point>();
		tempP.put("hiya", new Point(new Coord((float) 1.1, 0), "hiya", "id", tempM.getName()));
		tempM.setAllPoints(tempP);

		Serialization.write(tempM);

		Map temp2 = Serialization.read(tempM.getName());

		System.out.println("Output:\t\t\t" + temp2.getName() + "\t" + temp2.getAllPoints().get("hiya").getCoord().getX());
		System.out.println("Expected:\t\tnew_map\t\t1.1");
	}

}
