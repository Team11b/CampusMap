package WPI.CampusMap.AStar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Max Stenke
 *
 */
public class Path {

	private static final float PATHTOLERANCE = (float) 0.1;
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
				// check if next point is on the same level as i - 1 and i + 1
				if (checkHorizontal(path.get(i - 1).getPoint(), path.get(i).getPoint(), path.get(i + 1).getPoint())) {
					System.out.println("Abridge one node horizontal");
					continue;
					// check if next point is on the same level as i - 1 and i +
					// 1
				} else if (checkVertical(path.get(i - 1).getPoint(), path.get(i).getPoint(),
						path.get(i + 1).getPoint())) {
					System.out.println("Abridge one node vertical");
					continue;
				} else {

					if (checkDiagonal(path.get(i - 1).getPoint(), path.get(i).getPoint(), path.get(i + 1).getPoint())) {
						System.out.println("Abridge one node diagonal");
						continue;
					}
				}
				temp.add(path.get(i));
			} else {
				temp.add(path.get(i));
			}
		}
		temp.add(last);
		return new Path(temp);
	}

	public boolean checkVertical(Point before, Point current, Point after) {
		float dif1 = Math.abs(current.getCoord().getX() - before.getCoord().getX());
		float dif2 = Math.abs(current.getCoord().getX() - after.getCoord().getX());
		if (dif1 <= current.getCoord().getX() * PATHTOLERANCE && dif2 <= current.getCoord().getX() * PATHTOLERANCE) {
			return true;
		}

		return false;
	}

	public boolean checkHorizontal(Point before, Point current, Point after) {
		float dif1 = Math.abs(current.getCoord().getY() - before.getCoord().getY());
		float dif2 = Math.abs(current.getCoord().getY() - after.getCoord().getY());
		if (dif1 <= current.getCoord().getY() * PATHTOLERANCE && dif2 <= current.getCoord().getY() * PATHTOLERANCE) {
			return true;
		}

		return false;
	}

	public boolean checkDiagonal(Point before, Point current, Point after) {
		Coord deltaBefore = new Coord(Math.abs(current.getCoord().getX() - before.getCoord().getX()),
				Math.abs(current.getCoord().getY() - before.getCoord().getY()));
		Coord deltaAfter = new Coord(Math.abs(current.getCoord().getX() - after.getCoord().getX()),
				Math.abs(current.getCoord().getY() - after.getCoord().getY()));

		if ((deltaBefore.getX() == deltaAfter.getX()) && (deltaBefore.getY() == deltaAfter.getY())
				|| deltaBefore.getX() >= deltaAfter.getX() * (1 - PATHTOLERANCE)
						&& deltaBefore.getX() <= deltaAfter.getX() * (1 + PATHTOLERANCE)
						&& deltaBefore.getY() >= deltaAfter.getY() * (1 - PATHTOLERANCE)
						&& deltaBefore.getY() <= deltaAfter.getY() * (1 + PATHTOLERANCE)) {
			return true;
		}

		return false;
	}
	
	public String getAndDisplayDirections(Path path) {
		String route = "";
		for (int i = 1; i < path.getPath().size(); i++) {
			String turn = "";
			String direction = "";
			float dist = path.getPath().get(i).getPoint().distance(path.getPath().get(i - 1).getPoint());
			float angle = path.getAngle(path.getPath().get(i - 1).getPoint(), path.getPath().get(i).getPoint());

			route += path.getPath().get(i - 1).getPoint().getCoord().toString() + " to "
					+ path.getPath().get(i).getPoint().getCoord().toString() + "";
			if (path.getPath().get(i).getPoint().getCoord().getX() == path.getPath().get(i - 1).getPoint().getCoord()
					.getX()
					|| path.getPath().get(i).getPoint().getCoord().getY() == path.getPath().get(i - 1).getPoint()
							.getCoord().getY()) {
				route += "Walk " + dist + " feet straight on.\n";
			} else {

				if (path.getPath().get(i - 1).getPoint().getCoord().getX() < path.getPath().get(i).getPoint().getCoord()
						.getX()) {
					System.out.println(angle);
					if (angle < 0)
						turn = "left";
					else
						turn = "right";

				}
				if (Math.abs(angle) > 0 && Math.abs(angle) < 45) {
					direction = "slightly";
				} else if (Math.abs(angle) > 45 && Math.abs(angle) < 90) {
					direction = "hard";
				}
				route += "Turn " + direction + " " + turn + " and walk " + dist + " feet\n";
			}
		}

		return route;
	}

	/**
	 * Calculates the angle between to points
	 * 
	 * @param point1
	 *            Point 1
	 * @param point2
	 *            Point 2
	 * @return returns the angle.
	 */

	public float getAngle(Point point1, Point point2) {
		return (float) ((float) Math.atan((point2.getCoord().getY() - point1.getCoord().getY())
				/ (point2.getCoord().getX() - point1.getCoord().getX())) * 180 / Math.PI);
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
