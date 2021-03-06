package WPI.CampusMap.BackendTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

public class RemovePointTest {
	@Ignore
	@Test
	public void test() throws XMLStreamException {
		IMap testMap = AllMaps.getInstance().getMap("AK");
		assertTrue(testMap.removePoint("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		
		Collection<RealPoint> points = testMap.getAllPoints();
		
		//checking points
		assertEquals(4, points.size());
		assertTrue(points.contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.contains("67573704-c76e-4356-a80e-b03142cf8af6"));
		assertTrue(points.contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(points.contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));
		
		//checking neighbors for zeroth point
//		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID().contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
//		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID().contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for first point
//		assertTrue(points.get("67573704-c76e-4356-a80e-b03142cf8af6").getNeighborsID().contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));
		
		//checking neighbors for second point
//		assertTrue(points.get("86ee014d-3e62-44f7-86e1-70a865afe51c").getNeighborsID().contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));

		//checking neighbors for third point
//		assertTrue(points.get("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1").getNeighborsID().contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
//		assertTrue(points.get("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1").getNeighborsID().contains("67573704-c76e-4356-a80e-b03142cf8af6"));

	}

}
