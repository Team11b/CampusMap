package WPI.CampusMap.PathPlanning.Route;

import java.util.ArrayList;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Route {
	private LinkedList<Instruction> route;

	public Route(LinkedList<Instruction> route) {
		this.route = route;
	}

	public Route() {
		this.route = new LinkedList<Instruction>();
	}

	public Route(MultiPath mp) {
		this.route = new LinkedList<Instruction>();
		parse(mp);
	}

	public LinkedList<Instruction> getRoute() {
		return route;
	}

	public void setRoute(LinkedList<Instruction> route) {
		this.route = route;
	}

	public void append(LinkedList<Instruction> newPart) {
		for (int i = 0; i < newPart.size(); i++) {
			this.route.add(newPart.get(i));
		}
	}

	public void parse(MultiPath mp) {
		LinkedList<Path> allPaths = mp.getMp();
		Path current = null;
		int offset = 0;

		for (int j = 0; j < allPaths.size(); j++) {
			current = allPaths.get(j);
			this.append(Route.parse(current, offset));
			offset += current.getPath().size();
		}
	}

	public static LinkedList<Instruction> parse(Path p, int offset) {
		LinkedList<Instruction> list = new LinkedList<Instruction>();
		Instruction latest = null;

		String route = "Start: " + p.getPath().get(0).getPoint().getCoord().toString() + "\n";
		route += "Face " + p.getPath().get(0).getPoint().getId() + " and walk "
				+ p.getPath().get(0).getPoint().distance(p.getPath().get(1).getPoint()) + "feet.\n";
		latest = new Instruction(route, 0, p.getPath().get(0), offset);
		list.add(latest);
		route = "";

		for (int i = 1; i < p.getPath().size() - 1; i++) {
			String turn = "";
			String direction = "";
			float dist = (float) p.getPath().get(i).getPoint().distance(p.getPath().get(i - 1).getPoint());

			float angleBefore = p.getAngle(p.getPath().get(i - 1).getPoint(), p.getPath().get(i).getPoint());
			float angleAfter = p.getAngle(p.getPath().get(i).getPoint(), p.getPath().get(i + 1).getPoint());
			// System.out.printf("Angle Before: %f, Angle After: %f \n",
			// angleBefore, angleAfter);

			route += p.getPath().get(i - 1).getPoint().getCoord().toString() + " to "
					+ p.getPath().get(i).getPoint().getCoord().toString() + "";

			int quad1 = (int) (((angleBefore < 0 ? 360 : 0) + angleBefore) / 90 + 1);
			int quad2 = (int) (((angleAfter < 0 ? 360 : 0) + angleAfter) / 90 + 1);
			// System.out.printf("Quad Before: %d, Quad After: %d \n", quad1,
			// quad2);
			if (quad1 == quad2)
				if (angleAfter > angleBefore)
					turn = "left";
				else
					turn = "right";
			else if (quad2 == (quad1 + 1) % 4)
				turn = "left";
			else
				turn = "right";
			if (Math.abs(angleBefore - angleAfter) < 45) {
				direction = "slightly ";
			} else {
				direction = "";
			}
			route += "Turn " + direction + turn + " and walk " + dist + " feet.\n";

			latest = new Instruction(route, dist, p.getPath().get(0), offset + i);
			list.add(latest);
			route = "";
		}

		return list;
	}
	
	/**
	 * Parses the current path of Nodes and returns a new Path with only the
	 * start, goal, and any Nodes that result in a change of direction
	 * 
	 * @return abridged list of Nodes
	 */
	public static Path getTurns(Path p) {
		ArrayList<Node> temp = new ArrayList<Node>();
		ArrayList<Node> path = p.getPath();
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
	
	public String toString() {
		String answer = "";
		
		for (Instruction i : this.route) {
			answer += i.toString();
			answer += "\n";
		}
		
		return answer;
	}

}
