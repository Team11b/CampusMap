package WPI.CampusMap.XMLTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;

public class XMLParseTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Ignore
	@Test
	public void testOpenXML() throws FileNotFoundException, XMLStreamException {
		Map testMap = new Map("AK");

		HashMap<String, Point> points = testMap.getAllPoints();

		assertEquals(20, testMap.getScale(), .10);
		// checking points
		assertEquals(5, points.size());
		assertTrue(points.containsKey("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.containsKey("67573704-c76e-4356-a80e-b03142cf8af6"));
		assertTrue(points.containsKey("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(points.containsKey("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));
		assertTrue(points.containsKey("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));

		// checking neighbors for zeroth point
		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID()
				.contains("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID()
				.contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(points.get("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48").getNeighborsID()
				.contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		// checking neighbors for first point
		assertTrue(points.get("67573704-c76e-4356-a80e-b03142cf8af6").getNeighborsID()
				.contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		// checking neighbors for second point
		assertTrue(points.get("86ee014d-3e62-44f7-86e1-70a865afe51c").getNeighborsID()
				.contains("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		assertTrue(points.get("86ee014d-3e62-44f7-86e1-70a865afe51c").getNeighborsID()
				.contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));

		// checking neighbors for second point
		assertTrue(points.get("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1").getNeighborsID()
				.contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.get("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1").getNeighborsID()
				.contains("67573704-c76e-4356-a80e-b03142cf8af6"));

		// checking neighbors for fourth point
		assertTrue(points.get("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde").getNeighborsID()
				.contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(points.get("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde").getNeighborsID()
				.contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
	}

	@Ignore // Ignore till we decide if FileNowFoundException is thrown by map
			// constructor
	@Test
	public void testOpenXMLFileNotFound() throws FileNotFoundException, XMLStreamException {
		exception.expect(FileNotFoundException.class);
		Map testMap = new Map("blah.xml");
	}

	@Ignore
	@Test
	public void testInvalidXML() throws FileNotFoundException, XMLStreamException {
		exception.expect(XMLStreamException.class);
		Map testMap = new Map("borked");
	}
}
