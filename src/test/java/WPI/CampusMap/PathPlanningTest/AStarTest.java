package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;
import WPI.CampusMap.PathPlanning.AStar.AStar;
import WPI.CampusMap.XML.XML;

public class AStarTest {
	static Map testMap, testMap2;

	@BeforeClass
	public static void getMap() throws XMLStreamException {
		testMap = new Map("5x5Test");
		testMap.setAllPoints(XML.parseXML(testMap));
		testMap2 = new Map("5x5Test2");
		testMap2.setAllPoints(XML.parseXML(testMap2));
	}

	@Test
	public void testAStar4to12() {
		Point start, goal;
		start = testMap.getPoint("4");
		goal = testMap.getPoint("12");

//		Path path = testMap.astar(start, goal);
		Path path = AStar.single_AStar(start, goal);
		ArrayList<Node> pathNodes = path.getPath();
		System.out.println("path: " + path.getPath().size());
		assertEquals(7, path.getPath().size());
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "9");
		assertEquals(pathNodes.get(2).getPoint().getId(), "14");
		assertEquals(pathNodes.get(3).getPoint().getId(), "19");
		assertEquals(pathNodes.get(4).getPoint().getId(), "18");
		assertEquals(pathNodes.get(5).getPoint().getId(), "17");
		assertEquals(pathNodes.get(6).getPoint().getId(), "12");
	}

	@Test
	public void testAStar4to11() {
		System.out.println("in");
		Point start, goal;
		start = testMap.getPoint("4");
		goal = testMap.getPoint("11");

		Path path = AStar.single_AStar(start, goal);
		ArrayList<Node> pathNodes = path.getPath();

		assertEquals(path.getPath().size(), 8);
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "3");
		assertEquals(pathNodes.get(2).getPoint().getId(), "2");
		assertEquals(pathNodes.get(3).getPoint().getId(), "1");
		assertEquals(pathNodes.get(4).getPoint().getId(), "0");
		assertEquals(pathNodes.get(5).getPoint().getId(), "5");
		assertEquals(pathNodes.get(6).getPoint().getId(), "10");
		assertEquals(pathNodes.get(7).getPoint().getId(), "11");
	}

	@Test
	public void testAStar4to11_2() {
		System.out.println("in");
		Point start, goal;
		start = testMap2.getPoint("4");
		goal = testMap2.getPoint("12");

		Path path = AStar.single_AStar(start, goal);
		ArrayList<Node> pathNodes = path.getPath();

		assertEquals(path.getPath().size(), 7);
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "9");
		assertEquals(pathNodes.get(2).getPoint().getId(), "14");
		assertEquals(pathNodes.get(3).getPoint().getId(), "19");
		assertEquals(pathNodes.get(4).getPoint().getId(), "18");
		assertEquals(pathNodes.get(5).getPoint().getId(), "17");
		assertEquals(pathNodes.get(6).getPoint().getId(), "12");
	}

}