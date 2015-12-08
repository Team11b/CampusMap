package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;
import java.util.HashMap;

import WPI.CampusMap.Backend.Core.Map.AllMaps;

public class AllPoints {
	private static volatile AllPoints instance;
	//Contains only named points, key is the name of the point and value is the fullName
	private static HashMap<String, String> allPoints = new HashMap<String, String>();

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

	public void clearAllMaps() {
		allPoints.clear();
	}

	public String getPointFullName(String pointKey) {
		return AllPoints.allPoints.get(pointKey);
	}
	
	public IPoint getPoint(String pointKey) {
		String[] fullName = AllPoints.allPoints.get(pointKey).split("/");
		
		return AllMaps.getInstance().getMap(fullName[0]).getPoint(fullName[1]);
	}

	public boolean addPoint(String pointFullName) {
		String pointName = pointFullName.split("/")[1];
		if ((AllPoints.allPoints.containsKey(pointName))) {
				return false;
		}

		AllPoints.allPoints.put(pointName, pointFullName);
		return true;
	}

	public ArrayList<String> addAllPoints(ArrayList<String> multiPoints) {
		ArrayList<String> remaining = new ArrayList<String>();
		boolean result = false;

		for (int j = 0; j < multiPoints.size(); j++) {
			result = this.addPoint(multiPoints.get(j));

			if (!(result)) {
				remaining.add(multiPoints.get(j));
			}
		}

		return remaining;
	}

	public ArrayList<String> addAllPoints(String[] multiPoints) {
		ArrayList<String> remaining = new ArrayList<String>();
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
