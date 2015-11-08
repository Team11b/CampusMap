package WPI.CampusMap.AStar;

import java.util.ArrayList;
import java.util.Collections;

public class Path {

	private ArrayList<Node> path;

	public Path(ArrayList<Node> path) {
		this.path = path;
	}

	public Path() {
		this.path = new ArrayList<Node>();
	}

	public boolean addNode(Node node) {
		return path.add(node);
	}

	/**
	 * Reverses the ArrayList<Node> of this Path
	 */
	public void reverse() {
		Collections.reverse(this.path);
	}

	/**
	 * This method converts the A* star path into a clean path
	 * 
	 * @return an arraylist of nodes
	 */

	public Path getTurns() {
		ArrayList<Node> temp = new ArrayList<Node>();
		Node first = path.get(0);

		temp.add(first);
		for (int i = 1; i < path.size(); i++) {
			if (path.get(i + 1) != null) {
				// big chunk of if statements start
				// check if next point is on the same level as i - 1 and i + 1
				if ((getNodePointCoord(path.get(i)).getX() == getNodePointCoord(path.get(i + 1)).getX())
						&& (getNodePointCoord(path.get(i)).getX() == getNodePointCoord(path.get(i - 1)).getX())) {
					System.out.println("Abridge one node horizontal");
					break;
				} else if ((getNodePointCoord(path.get(i)).getY() == getNodePointCoord(path.get(i + 1)).getY())
						&& (getNodePointCoord(path.get(i)).getY() == getNodePointCoord(path.get(i - 1)).getY())) {
					System.out.println("Abridge one node vertical");
					break;
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
						System.out.println("Abridge one node vertical");
						break;
					}
				}
				// big chunk of if statements start
			} else {
				temp.add(path.get(i));
			}
		}

		return null;
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
			System.out.print("(" + path.getPath().get(i).getPoint().getCoord().getX() + ","
					+ path.getPath().get(i).getPoint().getCoord().getX() + ")");
		}
	}

}
