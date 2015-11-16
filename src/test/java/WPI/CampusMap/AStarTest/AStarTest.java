package WPI.CampusMap.AStarTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Node;
import WPI.CampusMap.AStar.Path;
import WPI.CampusMap.AStar.Point;

public class AStarTest {
	@Test
	public void testAStar4to12() throws FileNotFoundException,XMLStreamException {
		Map testMap = new Map("XML/5x5Test.xml");
		Point start,goal;
		start = testMap.getPoint("4");
		goal = testMap.getPoint("12");
		
		Path path = testMap.astar(start, goal);
		ArrayList<Node> pathNodes = path.getPath();

		assertEquals(path.getPath().size(),7);
		assertEquals(pathNodes.get(0).getPoint().getId(),4);
		assertEquals(pathNodes.get(0).getPoint().getId(),9);
		assertEquals(pathNodes.get(0).getPoint().getId(),14);
		assertEquals(pathNodes.get(0).getPoint().getId(),19);
		assertEquals(pathNodes.get(0).getPoint().getId(),18);
		assertEquals(pathNodes.get(0).getPoint().getId(),17);
		assertEquals(pathNodes.get(0).getPoint().getId(),12);
	}

}
