package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;
import java.util.HashMap;

public class AllPoints {
	private static volatile AllPoints instance;
	private static HashMap<String, IPoint> allPoints = new HashMap<String, IPoint>();

	private AllPoints() {
	}

	public static AllPoints getInstance() {
		if (instance == null) {
			synchronized (AllPoints.class) {
				if (instance == null) {
					instance = new AllPoints();
				}
			}
		}
		return instance;
	}

	public void clearAllPoints() {
		allPoints.clear();
	}

	public IPoint getPoint(String pointKey) {
		return AllPoints.allPoints.get(pointKey);
	}

	public boolean addPoint(IPoint pointValue) {
		if ((AllPoints.allPoints.containsKey(pointValue.getId()))) {
			if (!(pointValue.getId().contains("-"))) {
				return false;
			}
		}

		AllPoints.allPoints.put(pointValue.getId(), pointValue);
		return true;
	}

	public ArrayList<IPoint> addAllPoints(ArrayList<RealPoint> multiPoints) {
		ArrayList<IPoint> remaining = new ArrayList<IPoint>();
		boolean result = false;

		for (int j = 0; j < multiPoints.size(); j++) {
			result = this.addPoint(multiPoints.get(j));

			if (!(result)) {
				remaining.add(multiPoints.get(j));
			}
		}

		return remaining;
	}

	public ArrayList<IPoint> addAllPoints(IPoint[] multiPoints) {
		ArrayList<IPoint> remaining = new ArrayList<IPoint>();
		boolean result = false;

		for (int j = 0; j < multiPoints.length; j++) {
			result = this.addPoint(multiPoints[j]);

			if (!(result)) {
				remaining.add(multiPoints[j]);
			}
		}

		return remaining;
	}

}
