package WPI.CampusMap.AStarTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class RemovePointTest {

	@Test
	public void test() throws XMLStreamException {
		Map testMap = new Map("AK");
		assertTrue(testMap.removePoint("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		
		HashMap<String, Point> points = testMap.getMap();
		
		//checking points
		assertEquals(4, points.size());
		assertTrue(points.containsKey("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.containsKey("67573704-c76e-4356-a80e-b03142cf8af6"));
		assertTrue(points.containsKey("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(points.containsKey("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));
		
		//checking neighbors for zeroth point
		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID().contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID().contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for first point
		assertTrue(points.get("67573704-c76e-4356-a80e-b03142cf8af6").getNeighborsID().contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));
		
		//checking neighbors for second point
		assertTrue(points.get("86ee014d-3e62-44f7-86e1-70a865afe51c").getNeighborsID().contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));

		//checking neighbors for third point
		assertTrue(points.get("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1").getNeighborsID().contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.get("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1").getNeighborsID().contains("67573704-c76e-4356-a80e-b03142cf8af6"));

	}

}
