package WPI.CampusMap.PathPlanning;

import java.util.ArrayList;
import java.util.Collections;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point;

/**
 * 
 * @author Max Stenke
 * @author Jacob Zizmor
 *
 */
public class Path {

	private float pathtolarence;
	private float pathtolarenceMultiplier = (float) (0.2);
	private ArrayList<Node> path;
	private String mapName;
	private float pathScale;

	/**
	 * Constructor with pre-defined ArrayList of Nodes
	 * @param path pre-defined ArrayList of Nodes
	 * @param mapName the name of the map this Path uses
	 * @param pathScale the scale of this Path
	 */
	public Path(ArrayList<Node> path, String mapName, float pathScale) {
		this.path = path;
		this.mapName = mapName;
		setScale(pathScale);
	}

	/**
	 * Constructor with no previous Nodes
	 */
	public Path() {
		this.path = new ArrayList<Node>();
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
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
			// check if next point is on the same level as i - 1 and i + 1
			if (checkHorizontal(path.get(i - 1).getPoint(), path.get(i).getPoint(), path.get(i + 1).getPoint())) {
				// System.out.println("Abridge one node horizontal");
				continue;
				// check if next point is on the same level as i - 1 and i +
				// 1
			} else if (checkVertical(path.get(i - 1).getPoint(), path.get(i).getPoint(), path.get(i + 1).getPoint())) {
				// System.out.println("Abridge one node vertical");
				continue;
			} else {

				if (checkDiagonal(path.get(i - 1).getPoint(), path.get(i).getPoint(), path.get(i + 1).getPoint())) {
					// System.out.println("Abridge one node diagonal");
					continue;
				}
			}
			temp.add(path.get(i));

		}
		temp.add(last);
		return new Path(temp, this.mapName, this.pathScale);
	}

	/**
	 * Check if a point is in a vertical line between two other points
	 * 
	 * @param before
	 *            Point before the current point
	 * @param current
	 *            Current point
	 * @param after
	 *            Point after the current point
	 * @return Returns true if it's vertical aligned otherwise false
	 */
	public boolean checkVertical(Point before, Point current, Point after) {
		float dif1 = Math.abs(current.getCoord().getX() - before.getCoord().getX());
		float dif2 = Math.abs(current.getCoord().getX() - after.getCoord().getX());
		if (dif1 <= pathtolarence && dif2 <= pathtolarence) {
			return true;
		}

		return false;
	}

	/**
	 * Check if point is in a horizontal line between two other points
	 * 
	 * @param before
	 *            Point before the current point
	 * @param current
	 *            Current point
	 * @param after
	 *            Point after the current point
	 * @return Returns true if it's horizontal aligned otherwise false
	 */
	public boolean checkHorizontal(Point before, Point current, Point after) {
		float dif1 = Math.abs(current.getCoord().getY() - before.getCoord().getY());
		float dif2 = Math.abs(current.getCoord().getY() - after.getCoord().getY());
		if (dif1 <= pathtolarence && dif2 <= pathtolarence) {
			return true;
		}

		return false;
	}

	/**
	 * Check if point is in a diagonal line between two other points
	 * 
	 * @param before
	 *            Point before the current point
	 * @param current
	 *            Current point
	 * @param after
	 *            Point after the current point
	 * @return Returns true if it's diagonal aligned otherwise false
	 */
	public boolean checkDiagonal(Point before, Point current, Point after) {

		Coord slope = new Coord(Math.abs(current.getCoord().getX() - before.getCoord().getX()),
				Math.abs(current.getCoord().getY() - before.getCoord().getY()));
		Coord deltaAfter = new Coord(Math.abs(current.getCoord().getX() - after.getCoord().getX()),
				Math.abs(current.getCoord().getY() - after.getCoord().getY()));
		float expectedY = slope.getY() / slope.getX() * deltaAfter.getX();
		if (Math.abs(expectedY - deltaAfter.getY()) < pathtolarence) {
			return true;
		}

		return false;
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
		return (float) (-(float) Math.atan2((point2.getCoord().getY() - point1.getCoord().getY()),
				(point2.getCoord().getX() - point1.getCoord().getX())) * 180 / Math.PI);
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
	
	/**
	 * Checks if two Paths are equal based upon the ID of each Point in the Path
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Path)) {
			return false;
		}
		if (((Path)(other)).getPath().size() != this.getPath().size()) {
			return false;
		}
		
		for (int j = 0; j < ((Path)(other)).getPath().size(); j++) {
			if (!(((Path)(other)).getPath().get(j).getPoint().getId().equals(this.getPath().get(j).getPoint().getId()))) {
				return false;
			}
		}
		
		return true;
	}
	
	public void setScale(float scale) {
		pathScale = scale;
		pathtolarence = (float) (pathtolarenceMultiplier / pathScale);
	}
	
}
