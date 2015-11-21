package WPI.CampusMap.AStarTest;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.BeforeClass;
import org.junit.Test;

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
		beta = new Node(new Point(new Coord(1.0, 1.0), Point.HALLWAY, "beta"), null);
		gamma = new Node(new Point(new Coord(2.0, 2.0), Point.HALLWAY, "gamma"), null);
		delta = new Node(new Point(new Coord(3.0, 3.0), Point.HALLWAY, "delta"), null);
		eta = new Node(new Point(new Coord(4.0, 4.0), Point.HALLWAY, "eta"), null);
		iota = new Node(new Point(new Coord(5.0, 5.0), Point.HALLWAY, "iota"), null);
	}

	@Test
	public void testAdd() {
		testing1.add(beta);
		testing2.add(beta);
		
		testing1.add(gamma);
		testing2.add(gamma);
		
		testing1.add(alpha);
		testing2.add(alpha);
		
		testing1.add(iota);
		testing2.add(iota);
		System.out.println(testing1.size());
		assertTrue(testing1.size() == 4);
		assertTrue(testing2.size() == 4);
	}

}
