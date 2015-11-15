package WPI.CampusMap.XMLTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;
import WPI.CampusMap.XML.XML;

public class XMLWriteTest {

	@Test
	public void testOpenXML() throws FileNotFoundException,XMLStreamException {
		Map testMap = new Map("XML/AK.xml");
		testMap.setName("AKTest");
		XML.writePoints(testMap, testMap.getMap());
		testMap = new Map("XML/AKTest.xml");
		ArrayList<Point> points = testMap.getMap();
		
		//checking points
		assertEquals(5, points.size());
		assertEquals("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48", points.get(0).getId());
		assertEquals("67573704-c76e-4356-a80e-b03142cf8af6", points.get(1).getId());
		assertEquals("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1", points.get(2).getId());
		assertEquals("86ee014d-3e62-44f7-86e1-70a865afe51c", points.get(3).getId());
		assertEquals("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde", points.get(4).getId());

		//checking points coords
		assertEquals(new Coord((float)-116.6355,(float)96.44861), points.get(0).getCoord());
		assertEquals(new Coord((float)-294.5794,(float)245.9813), points.get(1).getCoord());
		assertEquals(new Coord((float)-397.757,(float)60.56075), points.get(2).getCoord());
		assertEquals(new Coord((float)-321.4953,(float)-106.9159), points.get(3).getCoord());
		assertEquals(new Coord((float)-71.77567,(float)-100.9346), points.get(4).getCoord());
		
		//checking neighbors for zeroth point
		assertTrue(Arrays.asList(points.get(0).getNeighborsID()).contains("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		assertTrue(Arrays.asList(points.get(0).getNeighborsID()).contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));
		assertTrue(Arrays.asList(points.get(0).getNeighborsID()).contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for first point
		assertTrue(Arrays.asList(points.get(1).getNeighborsID()).contains("8c45166a-15fc-41b9-8a7a-e27e7d1c9cd1"));

		//checking neighbors for second point
		assertTrue(Arrays.asList(points.get(2).getNeighborsID()).contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(Arrays.asList(points.get(2).getNeighborsID()).contains("67573704-c76e-4356-a80e-b03142cf8af6"));

		//checking neighbors for third point
		assertTrue(Arrays.asList(points.get(3).getNeighborsID()).contains("b9e43a3e-c47d-4f7b-b63c-f874c94f4cde"));
		assertTrue(Arrays.asList(points.get(3).getNeighborsID()).contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		
		//checking neighbors for fourth point
		assertTrue(Arrays.asList(points.get(4).getNeighborsID()).contains("1fe6c8ad-437c-4666-b66b-c83cf9bf1e48"));
		assertTrue(Arrays.asList(points.get(4).getNeighborsID()).contains("86ee014d-3e62-44f7-86e1-70a865afe51c"));		
		//Delete testMap
		Path path = Paths.get("XML\\AKTest.xml");
		try {
		    Files.delete(path);
		}catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}

}
