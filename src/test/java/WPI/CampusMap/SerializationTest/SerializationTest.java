package WPI.CampusMap.SerializationTest;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import Serialization.Serialization;
import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class SerializationTest {

	@Test
	public void test() {
		Map tempM = new Map();
		tempM.setName("test_map");
		HashMap<String, Point> tempP = new HashMap<String, Point>();
		tempP.put("hiya", new Point(new Coord((float) 1.1, 0), "hiya", "id"));
		tempM.setMap(tempP);

		Serialization.write(tempM);

		Map temp2 = Serialization.read(tempM.getName());
		System.out.println("Output:\t\t\t" + temp2.getName() + "\t" + temp2.getMap().get("hiya").getCoord().getX());
		System.out.println("Expected:\t\tnew_map\t1.1");
	}

}
