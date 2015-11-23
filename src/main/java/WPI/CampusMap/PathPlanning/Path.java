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

	private static final float PATHTOLERANCE = (float) 0.1;
	private ArrayList<Node> path;
	private String mapName;

	/**
	 * Constructor with pre-defined ArrayList of Nodes
	 * 
	 * @param path
	 *            pre-defined ArrayList of Nodes
	 * @param mapName the name of the map this Path uses
	 */
	public Path(ArrayList<Node> path, String mapName) {
		this.path = path;
		this.mapName = mapName;
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
		return null;
	}

	private boolean checkHorizontal(Point before, Point current, Point after) {
		return false;
	}

	private boolean checkVertical(Point before, Point current, Point after) {
		return false;
	}

	private boolean checkDiagonal(Point before, Point current, Point after) {
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
		return 0;
	}

	public Coord getNodePointCoord(Node node) {
		return null;
	}

	public ArrayList<Node> getPath() {
		return this.path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}

	public void pathToString(Path path) {
	}

	/**
	 * Calculates the walking path and displays the directions.
	 */
	public static String getAndDisplayDirections(Path path) {
		return null;
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
	
}
