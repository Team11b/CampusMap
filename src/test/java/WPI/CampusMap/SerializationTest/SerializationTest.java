package WPI.CampusMap.SerializationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Map.RealMap;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class SerializationTest {

	static ProxyMap pm;
	static ProxyMap connected;

	@BeforeClass
	public static void setup() {
		AllMaps.getInstance().clearAllMaps();
		pm = new ProxyMap("testmap");
		pm.addPoint(new RealPoint(new Coord(1.0, 0.0), RealPoint.ELEVATOR, "1", pm.getName()));
		
		AllMaps.getInstance().addMap(pm);

		connected = new ProxyMap("this_is_connected");
		connected.addPoint(new RealPoint(new Coord(1.0, 0.0), RealPoint.ELEVATOR, "1", connected.getName()));
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
		
		System.out.println(newPm.getAllPoints().size());
		System.out.println(pm.getAllPoints().size());
		
		assertTrue(newPm.getAllPoints().equals(pm.getAllPoints()));
		assertTrue(newCon.getAllPoints().equals(connected.getAllPoints()));
		
		assertTrue(newPm.getPoint("1").getNeighborsP().equals(pm.getPoint("1").getNeighborsP()));
		assertTrue(newCon.getPoint("1").getNeighborsP().equals(connected.getPoint("1").getNeighborsP()));
		
		assertTrue(newPm.getAllPoints().equals(newRPm.getAllPoints()));
		assertTrue(newCon.getAllPoints().equals(newRCon.getAllPoints()));
		
		assertTrue(newPm.getPoint("1").getNeighborsP().equals(newRPm.getPoint("1").getNeighborsP()));
		assertTrue(newCon.getPoint("1").getNeighborsP().equals(newRCon.getPoint("1").getNeighborsP()));

		assertEquals(newPm.getPoint("1").getType(), RealPoint.ELEVATOR);
		assertEquals(newCon.getPoint("1").getType(), RealPoint.ELEVATOR);
		assertEquals(newCon.getPoint("2").getType(), RealPoint.HALLWAY);

	}

}
