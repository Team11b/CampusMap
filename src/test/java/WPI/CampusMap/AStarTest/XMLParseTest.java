package WPI.CampusMap.AStarTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class XMLParseTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testOpenXML() throws FileNotFoundException,XMLStreamException {
		Map testMap = new Map("XML/AK.xml");
		Point[] points = testMap.getMap();
		
		//checking points
		assertEquals(5, points.length);
		assertEquals("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48", points[0].getId());
		assertEquals("67573704-c76e-4356-a80e-b03142cf8af6", points[1].getId());
		assertEquals("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1", points[2].getId());
		assertEquals("86ee014d-3e62-44f7-86e1-70a865afe51c", points[3].getId());
		assertEquals("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde", points[4].getId());

		//checking points coords
		assertEquals(new Coord((float)-116.6355,(float)96.44861), points[0].getCoord());
		assertEquals(new Coord((float)-294.5794,(float)245.9813), points[1].getCoord());
		assertEquals(new Coord((float)-397.757,(float)60.56075), points[2].getCoord());
		assertEquals(new Coord((float)-321.4953,(float)-106.9159), points[3].getCoord());
		assertEquals(new Coord((float)-71.77567,(float)-100.9346), points[4].getCoord());
		
		//checking neighbors for zeroth point
		assertTrue(Arrays.asList(points[0].getNeighborsID()).contains("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		assertTrue(Arrays.asList(points[0].getNeighborsID()).contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(Arrays.asList(points[0].getNeighborsID()).contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for first point
		assertTrue(Arrays.asList(points[1].getNeighborsID()).contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for second point
		assertTrue(Arrays.asList(points[2].getNeighborsID()).contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(Arrays.asList(points[2].getNeighborsID()).contains("67573704-c76e-4356-a80e-b03142cf8af6"));

		//checking neighbors for third point
		assertTrue(Arrays.asList(points[3].getNeighborsID()).contains("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		assertTrue(Arrays.asList(points[3].getNeighborsID()).contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		
		//checking neighbors for fourth point
		assertTrue(Arrays.asList(points[4].getNeighborsID()).contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(Arrays.asList(points[4].getNeighborsID()).contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
	}

	@Test
	public void testOpenXMLFileNotFound() throws FileNotFoundException,XMLStreamException {
	    exception.expect(FileNotFoundException.class);
	    Map testMap = new Map("blah.xml");
	}
	
	@Test
	public void testInvalidXML() throws FileNotFoundException,XMLStreamException {
	    exception.expect(XMLStreamException.class);
	    Map testMap = new Map("XML/borked.xml");
	}
}
