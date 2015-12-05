package WPI.CampusMap.SerializationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Map.RealMap;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Recording.Serialization.OLSSerializer;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class SerializationTest {

	static ProxyMap pm;
	static ProxyMap connected;

	@BeforeClass
	public static void setup() {
		AllMaps.getInstance().clearAllMaps();
		pm = new ProxyMap("testmap");
		pm.addPoint(new RealPoint(new Coord(1.0, 0.0), RealPoint.HALLWAY, "1", pm.getName()));
		
		AllMaps.getInstance().addMap(pm);

		connected = new ProxyMap("this_is_connected");
		connected.addPoint(new RealPoint(new Coord(1.0, 0.0), RealPoint.HALLWAY, "1", connected.getName()));
		connected.addPoint(new RealPoint(new Coord(1.0, 1.0), RealPoint.HALLWAY, "2", connected.getName()));

		AllMaps.getInstance().addMap(connected);
		
		connected.addEdge(connected.getPoint("1"), pm.getPoint("1"));
	}

	@AfterClass
	public static void cleanup() {
		AllMaps.getInstance().clearAllMaps();

		// Delete written file
		Path path = Paths.get(Serializer.folderProxy + pm.getName() + Serializer.fileType);
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

		// Delete written file
		path = Paths.get(Serializer.folderProxy + connected.getName() + Serializer.fileType);
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

		// Delete written file
		path = Paths.get(Serializer.folderReal + pm.getName() + Serializer.fileType);
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

		// Delete written file
		path = Paths.get(Serializer.folderReal + connected.getName() + Serializer.fileType);
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

	@SuppressWarnings("unused")
	@Test
	public void testProxy() {
		pm.save();
		connected.save();
		
		System.out.println(pm.getName());
		ProxyMap newPm = Serializer.proxyLoad(pm.getName());
		ProxyMap newCon = Serializer.proxyLoad(connected.getName());
		System.out.println(newPm.getName());

		RealMap newRPm = Serializer.realLoad(newPm.getName());
		RealMap newRCon = Serializer.realLoad(newCon.getName());
		newRPm.validatePoints();
		newRCon.validatePoints();
		
		System.out.println(newPm.getAllPoints().length);
		System.out.println(pm.getAllPoints().length);
		
		assertTrue(Arrays.equals(newPm.getAllPoints(),pm.getAllPoints()));
		assertTrue(Arrays.equals(newCon.getAllPoints(),connected.getAllPoints()));
		
		assertTrue(newPm.getAllPoints()[0].getNeighborsP().equals(pm.getAllPoints()[0].getNeighborsP()));
		assertTrue(newCon.getAllPoints()[0].getNeighborsP().equals(connected.getAllPoints()[0].getNeighborsP()));
		
		assertTrue(Arrays.equals(newPm.getAllPoints(),newRPm.getAllPoints()));
		assertTrue(Arrays.equals(newCon.getAllPoints(),newRCon.getAllPoints()));
		
		assertTrue(newPm.getAllPoints()[0].getNeighborsP().equals(newRPm.getAllPoints()[0].getNeighborsP()));
		assertTrue(newCon.getAllPoints()[0].getNeighborsP().equals(newRCon.getAllPoints()[0].getNeighborsP()));

		assertTrue(true);
	}

	@Ignore
	@Test
	public void test() {
		Map tempM = new Map("tempM");

		Point oneP = new Point(new Coord(0, 0), "", "PointOne", "tempM");
		Point twoP = new Point(new Coord(1, 1), "", "PointTwo", "tempM");
		Point threeP = new Point(new Coord(0, 1), "", "PointThree", "tempM");
		tempM.addPoint(oneP);
		tempM.addPoint(twoP);
		tempM.addPoint(threeP);

		assertTrue(tempM.addEdge(tempM.getPoint("PointOne"), tempM.getPoint("PointTwo")));
		assertTrue(tempM.addEdge(tempM.getPoint("PointThree"), tempM.getPoint("PointOne")));
		assertTrue(tempM.addEdge(tempM.getPoint("PointThree"), tempM.getPoint("PointTwo")));

		OLSSerializer.write(tempM);

		Map temp2 = OLSSerializer.read(tempM.getName());

		assertEquals(tempM.getPoint("PointOne"), temp2.getPoint("PointOne"));
		assertEquals(tempM.getPoint("PointTwo"), temp2.getPoint("PointTwo"));
		assertEquals(tempM.getPoint("PointThree"), temp2.getPoint("PointThree"));

		assertEquals(tempM.getPoint("PointOne").getNeighborsP(), temp2.getPoint("PointOne").getNeighborsP());
		assertEquals(tempM.getPoint("PointTwo").getNeighborsP(), temp2.getPoint("PointTwo").getNeighborsP());
		assertEquals(tempM.getPoint("PointThree").getNeighborsP(), temp2.getPoint("PointThree").getNeighborsP());

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
