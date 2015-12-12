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
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Map.RealMap;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.PathPlanning.Node;
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
		
		ProxyMap newPm = Serializer.proxyLoad(pm.getName());
		ProxyMap newCon = Serializer.proxyLoad(connected.getName());

		RealMap newRPm = Serializer.realLoad(newPm.getName());
		RealMap newRCon = Serializer.realLoad(newCon.getName());
		newRPm.validatePoints();
		newRCon.validatePoints();
		RealPoint[] t1 = newPm.getAllPoints().toArray(new RealPoint[1]);
		RealPoint[] t2 = pm.getAllPoints().toArray(new RealPoint[pm.getAllPoints().size()]);
		
		assertTrue(Arrays.equals(t1, t2));
		assertTrue(Arrays.equals(newCon.getAllPoints().toArray(new RealPoint[1]),connected.getAllPoints().toArray(new RealPoint[1])));
		
		assertTrue(newPm.getPoint("1").getNeighborsP().equals(pm.getPoint("1").getNeighborsP()));
		assertTrue(newCon.getPoint("1").getNeighborsP().equals(connected.getPoint("1").getNeighborsP()));

		assertTrue(Arrays.equals(newPm.getAllPoints().toArray(new RealPoint[1]),newRPm.getAllPoints().toArray(new RealPoint[1])));
		assertTrue(Arrays.equals(newCon.getAllPoints().toArray(new RealPoint[1]),newRCon.getAllPoints().toArray(new RealPoint[1])));
		
		assertTrue(newPm.getPoint("1").getNeighborsP().equals(newRPm.getPoint("1").getNeighborsP()));
		assertTrue(newCon.getPoint("1").getNeighborsP().equals(newRCon.getPoint("1").getNeighborsP()));

		assertEquals(newPm.getPoint("1").getType(), RealPoint.ELEVATOR);
		assertEquals(newCon.getPoint("1").getType(), RealPoint.ELEVATOR);
		assertEquals(newCon.getPoint("2").getType(), RealPoint.HALLWAY);

		assertTrue(true);
	}

	@Test
	public void test() {
		AllMaps.getInstance().clearAllMaps();
		
		ProxyMap tempM = new ProxyMap("tempM");
		
		AllMaps.getInstance().addMap(tempM);

		RealPoint oneP = new RealPoint(new Coord(0, 0), RealPoint.HALLWAY, "PointOne", "tempM");
		RealPoint twoP = new RealPoint(new Coord(1, 1), RealPoint.HALLWAY, "PointTwo", "tempM");
		RealPoint threeP = new RealPoint(new Coord(0, 1), RealPoint.HALLWAY, "PointThree", "tempM");
		tempM.addPoint(oneP);
		tempM.addPoint(twoP);
		tempM.addPoint(threeP);

		assertTrue(tempM.addEdge(oneP, twoP));
		assertTrue(tempM.addEdge(threeP, twoP));
		assertTrue(tempM.addEdge(threeP, oneP));

		tempM.save();

		ProxyMap temp2 = Serializer.proxyLoad(tempM.getName());

		assertEquals(tempM.getPoint("PointOne"), temp2.getPoint("PointOne"));
		assertEquals(tempM.getPoint("PointTwo"), temp2.getPoint("PointTwo"));
		assertEquals(tempM.getPoint("PointThree"), temp2.getPoint("PointThree"));

		System.out.println(tempM.getPoint("PointOne").exists());
		System.out.println(tempM.getPoint("PointOne").getNeighborsP());
		assertEquals(tempM.getPoint("PointOne").getNeighborsP(), temp2.getPoint("PointOne").getNeighborsP());
		assertEquals(tempM.getPoint("PointTwo").getNeighborsP(), temp2.getPoint("PointTwo").getNeighborsP());
		assertEquals(tempM.getPoint("PointThree").getNeighborsP(), temp2.getPoint("PointThree").getNeighborsP());

		// Delete written file
		Path path = Paths.get(Serializer.folderReal + tempM.getName() + Serializer.fileType);
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
		

		path = Paths.get(Serializer.folderProxy + tempM.getName() + Serializer.fileType);
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
