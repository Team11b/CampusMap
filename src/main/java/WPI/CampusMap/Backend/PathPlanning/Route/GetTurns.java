package WPI.CampusMap.Backend.PathPlanning.Route;

import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;

public class GetTurns {
	public static float pathTolarence;
	public static float pathtolarenceMultiplier = (float) (0.2);

	public static ArrayList<IPoint> getTurns(Section section) {
		float scale = AllMaps.getInstance().getMap(section.getMap()).getScale();
		pathTolarence = (float) (pathtolarenceMultiplier / scale);

		ArrayList<IPoint> path = new ArrayList<IPoint>(section.getPoints());

		// System.out.println("Before Abridging: " + path.size());
		ArrayList<IPoint> temp = new ArrayList<IPoint>();
		IPoint first = path.get(0);
		IPoint last = path.get(path.size() - 1);

		temp.add(first);
		for (int i = 1; i < path.size() - 1; i++) {
			// check if next point is on the same level as i - 1 and i + 1
			if (checkHorizontal(path.get(i - 1), path.get(i), path.get(i + 1))) {
				// System.out.println("Abridge one node horizontal");
				continue;
				// check if next point is on the same level as i - 1 and i +
				// 1
			} else if (checkVertical(path.get(i - 1), path.get(i), path.get(i + 1))) {
				// System.out.println("Abridge one node vertical");
				continue;
			} else {

				// if (checkDiagonal(path.get(i - 1), path.get(i), path.get(i +
				// 1))) {
				// // System.out.println("Abridge one node diagonal");
				// continue;
				// }
			}
			temp.add(path.get(i));

		}
		temp.add(last);

		// System.out.println("After Abridging: " + temp.size());
		pathTolarence = 0;
		return temp;
	}

	/**
	 * Check if a point is in a vertical line between two other points
	 * 
	 * @param point
	 *            Point before the current point
	 * @param realPoint2
	 *            Current point
	 * @param realPoint3
	 *            Point after the current point
	 * @return Returns true if it's vertical aligned otherwise false
	 */
	private static boolean checkVertical(IPoint point, IPoint realPoint2, IPoint realPoint3) {
		float dif1 = Math.abs(realPoint2.getCoord().getX() - point.getCoord().getX());
		float dif2 = Math.abs(realPoint2.getCoord().getX() - realPoint3.getCoord().getX());
		if (dif1 <= pathTolarence && dif2 <= pathTolarence) {
			return true;
		}

		return false;
	}

	/**
	 * Check if point is in a horizontal line between two other points
	 * 
	 * @param point
	 *            Point before the current point
	 * @param realPoint2
	 *            Current point
	 * @param realPoint3
	 *            Point after the current point
	 * @return Returns true if it's horizontal aligned otherwise false
	 */
	private static boolean checkHorizontal(IPoint point, IPoint realPoint2, IPoint realPoint3) {
		// System.out.println("Before: "+before+", Current: " + current +"
		// After: "+after);
		float dif1 = Math.abs(realPoint2.getCoord().getY() - point.getCoord().getY());
		float dif2 = Math.abs(realPoint2.getCoord().getY() - realPoint3.getCoord().getY());
		// System.out.println(before.getCoord());
		// System.out.println(current.getCoord());
		// System.out.println(after.getCoord());
		// System.out.println("dif1: "+dif1+", dif2: " + dif2);
		// System.out.println("tolerance" + pathtolarence);
		// System.out.println();
		if (dif1 <= pathTolarence && dif2 <= pathTolarence) {
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
	@SuppressWarnings("unused")
	private boolean checkDiagonal(IPoint before, IPoint current, IPoint after) {

		Coord slope = new Coord(Math.abs(current.getCoord().getX() - before.getCoord().getX()),
				Math.abs(current.getCoord().getY() - before.getCoord().getY()));
		Coord deltaAfter = new Coord(Math.abs(current.getCoord().getX() - after.getCoord().getX()),
				Math.abs(current.getCoord().getY() - after.getCoord().getY()));
		float expectedY = slope.getY() / slope.getX() * deltaAfter.getX();
		if (Math.abs(expectedY - deltaAfter.getY()) < pathTolarence) {
			return true;
		}

		return false;
	}

	/**
	 * Calculates the angle between to points
	 * 
	 * @param Point
	 *            Point 1
	 * @param realPoint2
	 *            Point 2
	 * @return returns the angle.
	 */

	public static float getAngle(IPoint Point, IPoint realPoint2) {
		return (float) (-(float) Math.atan2((realPoint2.getCoord().getY() - Point.getCoord().getY()),
				(realPoint2.getCoord().getX() - Point.getCoord().getX())) * 180 / Math.PI);
	}

}
