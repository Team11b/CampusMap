package WPI.CampusMap.AStarTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class XMLParseTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testOpenXML() throws FileNotFoundException,XMLStreamException {
		Map testMap = new Map(0,"","XML/AK.xml");
	}

	@Test
	public void testOpenXMLFileNotFound() throws FileNotFoundException,XMLStreamException {
	    exception.expect(FileNotFoundException.class);
	    Map testMap = new Map(0,"","blah.xml");
	}
	
	@Test
	public void testInvalidXML() throws FileNotFoundException,XMLStreamException {
	    exception.expect(XMLStreamException.class);
	    Map testMap = new Map(0,"","XML/borked.xml");
	}
	

	@Test //need better test after stuff
	public void testMapCreation() throws FileNotFoundException,XMLStreamException {
		Map testMap = new Map(0,"","XML/AK.xml");
		Point[] points = testMap.getMap();

		System.out.printf("Number of points: %d\n", points.length);
		for(Point point:points){
			System.out.printf("Point ID: %s\n", point.getId());
			System.out.printf("X: %f, Y: %f\n", point.getCoord().getX(), point.getCoord().getX());
			System.out.printf("Type: %s\n", point.getType());
			System.out.printf("Number of neighbors: %d\n", point.getNeighborsP().length);
			String[] neighborID = point.getNeighborsID();
			for(String id:neighborID){
				System.out.printf("Neighbor: %s\n", id);
			}
			System.out.println();
		}
	}
}
