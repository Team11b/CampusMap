package WPI.CampusMap.AStarTest;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.AStar.Frontier;
import WPI.CampusMap.AStar.Frontier;
import WPI.CampusMap.AStar.Node;
import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point;

public class FrontierTest {
	public static Frontier<Node> testing1;
	public static Frontier<Node> testing2;

	public static Node alpha;
	public static Node beta;
	public static Node gamma;
	public static Node delta;
	public static Node eta;
	public static Node iota;

	@BeforeClass
	public static void beforeClass() {
		testing1 = new Frontier<Node>(new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				if (n1.getCurrentScore() < n2.getCurrentScore()) {
					return -1;
				} else if (n1.getCurrentScore() > n2.getCurrentScore()) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		testing2 = new Frontier<Node>(Frontier.stdNodeComp);

		alpha = new Node(new Point(new Coord(0.0, 0.0), Point.HALLWAY, "alpha"), null);
		alpha.setCumulativeDist(9.0);
		beta = new Node(new Point(new Coord(1.0, 1.0), Point.HALLWAY, "beta"), null);
		beta.setCumulativeDist(8.0);
		gamma = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "gamma"), null);
		gamma.setCumulativeDist(9.0);
		
		delta = new Node(new Point(new Coord(3.0, 3.0), Point.HALLWAY, "delta"), null);
		eta = new Node(new Point(new Coord(4.0, 4.0), Point.HALLWAY, "eta"), null);
		iota = new Node(new Point(new Coord(5.0, 5.0), Point.HALLWAY, "iota"), null);
	}

	@Test
	public void testAdd() {
		testing1.add(beta);
		testing2.add(beta);

		testing1.add(alpha);
		testing2.add(alpha);
		
		testing1.add(gamma);
		testing2.add(gamma);
		
		assertTrue(testing1.size() == 3);
		assertTrue(testing2.size() == 3);
	}
	
	@Test
	public void testRemove() {
		
		assertTrue(testing1.getNext().getPoint().getId().equals("beta"));
		assertTrue(testing2.getNext().getPoint().getId().equals("beta"));
		
		assertTrue(testing1.size() == 2);
		assertTrue(testing2.size() == 2);
	}

}
