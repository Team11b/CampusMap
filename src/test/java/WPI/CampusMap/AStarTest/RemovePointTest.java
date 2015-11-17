package WPI.CampusMap.AStarTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class RemovePointTest {

	@Test
	public void test() throws XMLStreamException {
		Map testMap = new Map("AK");
		testMap.removePoint("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde");
		
		ArrayList<Point> points = testMap.getMap();
		
		//checking points
		assertEquals(4, points.size());
		assertEquals("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48", points.get(0).getId());
		assertEquals("67573704-c76e-4356-a80e-b03142cf8af6", points.get(1).getId());
		assertEquals("86ee014d-3e62-44f7-86e1-70a865afe51c", points.get(2).getId());
		assertEquals("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1", points.get(3).getId());

		//checking points coords
		assertEquals(new Coord((float)-116.6355,(float)96.44861), points.get(0).getCoord());
		assertEquals(new Coord((float)-294.5794,(float)245.9813), points.get(1).getCoord());
		assertEquals(new Coord((float)-321.4953,(float)-106.9159), points.get(2).getCoord());
		assertEquals(new Coord((float)-397.757,(float)60.56075), points.get(3).getCoord());
		
		//checking neighbors for zeroth point
		assertTrue(points.get(0).getNeighborsID().contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(points.get(0).getNeighborsID().contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for first point
		assertTrue(points.get(1).getNeighborsID().contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));
		
		//checking neighbors for second point
		assertTrue(points.get(2).getNeighborsID().contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));

		//checking neighbors for third point
		assertTrue(points.get(3).getNeighborsID().contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.get(3).getNeighborsID().contains("67573704-c76e-4356-a80e-b03142cf8af6"));

	}

}
