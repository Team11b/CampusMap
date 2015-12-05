package WPI.CampusMap.SerializationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class SerializationTest {

	@Ignore
	@Test
	public void test() {
		Map tempM = new Map("tempM");

		Point oneP = new Point(new Coord(0,0), "", "PointOne", "tempM");
		Point twoP = new Point(new Coord(1,1), "", "PointTwo", "tempM");
		Point threeP = new Point(new Coord(0,1), "", "PointThree", "tempM");
		tempM.addPoint(oneP);
		tempM.addPoint(twoP);
		tempM.addPoint(threeP);
		
		assertTrue(tempM.addEdge(tempM.getPoint("PointOne"), tempM.getPoint("PointTwo")));
		assertTrue(tempM.addEdge(tempM.getPoint("PointThree"), tempM.getPoint("PointOne")));
		assertTrue(tempM.addEdge(tempM.getPoint("PointThree"), tempM.getPoint("PointTwo")));

		Serializer.write(tempM);

		Map temp2 = Serializer.read(tempM.getName());

		assertEquals(tempM.getPoint("PointOne"),temp2.getPoint("PointOne"));
		assertEquals(tempM.getPoint("PointTwo"),temp2.getPoint("PointTwo"));
		assertEquals(tempM.getPoint("PointThree"),temp2.getPoint("PointThree"));
		
		assertEquals(tempM.getPoint("PointOne").getNeighborsP(),
				temp2.getPoint("PointOne").getNeighborsP());
		assertEquals(tempM.getPoint("PointTwo").getNeighborsP(),
				temp2.getPoint("PointTwo").getNeighborsP());
		assertEquals(tempM.getPoint("PointThree").getNeighborsP(),
						temp2.getPoint("PointThree").getNeighborsP());
		
		// Delete written file
		Path path = Paths.get("serialized/tempM.ser");
		try {
			Files.delete(path);
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", path);
		} catch (IOException x) {
			// File permission problems are caught here.
			System.err.println(x);
		}
	}

}
