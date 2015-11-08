package WPI.CampusMap.AStar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Max
 *
 */
public class Path {

	private ArrayList<Node> path;

	/**
	 * Constructor with pre-defined ArrayList of Nodes
	 * 
	 * @param path
	 *            pre-defined ArrayList of Nodes
	 */
	public Path(ArrayList<Node> path) {
		this.path = path;
	}

	/**
	 * Constructor with no previous Nodes
	 */
	public Path() {
		this.path = new ArrayList<Node>();
	}

	public boolean addNode(Node node) {
		return path.add(node);
	}

	/**
	 * Reverses the ArrayList of this Path
	 */
	public void reverse() {
		Collections.reverse(this.path);
	}

	/**
	 * Parses the current path of Nodes and returns a new Path with only the
	 * start, goal, and any Nodes that result in a change of direction
	 * 
	 * @return abridged list of Nodes
	 */
	public Path getTurns() {
		ArrayList<Node> temp = new ArrayList<Node>();
		Node first = path.get(0);
		Node last = path.get(path.size() - 1);

		temp.add(first);
		for (int i = 1; i < path.size() - 1; i++) {
			System.out.println(i);
			if (i != path.size()) {
				// big chunk of if statements start
				// check if next point is on the same level as i - 1 and i + 1
				if ((getNodePointCoord(path.get(i)).getX() == getNodePointCoord(path.get(i + 1)).getX())
						&& (getNodePointCoord(path.get(i)).getX() == getNodePointCoord(path.get(i - 1)).getX())) {
					System.out.println("Abridge one node vertical");
					continue;
				} else if ((getNodePointCoord(path.get(i)).getY() == getNodePointCoord(path.get(i + 1)).getY())
						&& (getNodePointCoord(path.get(i)).getY() == getNodePointCoord(path.get(i - 1)).getY())) {
					System.out.println("Abridge one node horizontal");
					continue;
				} else {
					Coord deltaBefore = new Coord(
							Math.abs(getNodePointCoord(path.get(i)).getX() - getNodePointCoord(path.get(i - 1)).getX()),
							Math.abs(
									getNodePointCoord(path.get(i)).getY() - getNodePointCoord(path.get(i - 1)).getY()));
					Coord deltaAfter = new Coord(
							Math.abs(getNodePointCoord(path.get(i)).getX() - getNodePointCoord(path.get(i + 1)).getX()),
							Math.abs(
									getNodePointCoord(path.get(i)).getY() - getNodePointCoord(path.get(i + 1)).getY()));
					if ((deltaBefore.getX() == deltaAfter.getX()) && (deltaBefore.getY() == deltaAfter.getY())) {
						System.out.println("Abridge one node diagonal");
						continue;
					}
				}
				temp.add(path.get(i));
				// big chunk of if statements start
			} else {
				temp.add(path.get(i));
			}
		}
		temp.add(last);
		return new Path(temp);
	}

	public static void main(String[] args) {
		ArrayList<Node> turns = new ArrayList<Node>();
		turns.add(new Node(new Point(new Coord(0, 0), null, null), null));
		turns.add(new Node(new Point(new Coord(1, 0), null, null), null));
		turns.add(new Node(new Point(new Coord(2, 0), null, null), null));
		turns.add(new Node(new Point(new Coord(3, 0), null, null), null));
		turns.add(new Node(new Point(new Coord(4, 1), null, null), null));
		turns.add(new Node(new Point(new Coord(5, 2), null, null), null));
		turns.add(new Node(new Point(new Coord(6, 2), null, null), null));
		turns.add(new Node(new Point(new Coord(6, 3), null, null), null));
		turns.add(new Node(new Point(new Coord(5, 3), null, null), null));
		Path path = new Path(turns);
		path.pathToString(path);
		System.out.println("after get truns");
		path.pathToString(path.getTurns());

	}

	public Coord getNodePointCoord(Node node) {
		return node.getPoint().getCoord();
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}

	public void pathToString(Path path) {
		for (int i = 0; i < path.getPath().size(); i++) {
			System.out.println("(" + path.getPath().get(i).getPoint().getCoord().getX() + ","
					+ path.getPath().get(i).getPoint().getCoord().getY() + ")");
		}
	}

}
