package WPI.CampusMap.AStarTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Node;
import WPI.CampusMap.AStar.Path;
import WPI.CampusMap.AStar.Point;

public class AStarTest {

	@Test
	public void testAStar() throws FileNotFoundException,XMLStreamException {
		Map testMap = new Map(0, "","XML/5x5Test.xml");
		Point start,goal;
		start = testMap.getMap()[4];
		goal = testMap.getMap()[16];
		
		Path path = testMap.astar(start, goal);
		System.out.printf("Start: X: %f, Y: %f\n", start.getCoord().getX(), start.getCoord().getY());
		System.out.printf("Goal: X: %f, Y: %f\n", goal.getCoord().getX(), goal.getCoord().getY());
		System.out.printf("Number of steps: %d\n",path.getPath().size());
		for(Node node: path.getPath()){
			Coord coord =node.getPoint().getCoord();
			System.out.printf("X: %f, Y: %f\n",coord.getX(), coord.getY());
		}
	}

}
