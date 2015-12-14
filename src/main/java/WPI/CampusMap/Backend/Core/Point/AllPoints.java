package WPI.CampusMap.Backend.Core.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class AllPoints implements Serializable{


	private static final long serialVersionUID = -6405416499845660178L;
	private transient static volatile AllPoints instance;
	//Contains only named points, key is the name of the point and value is the fullName
	private static HashMap<String, String> allPoints = new HashMap<String, String>();

	private AllPoints() {
	}

	public static AllPoints getInstance() {
		if (instance == null) {
			synchronized (AllPoints.class) {
				if (instance == null) {
					instance = new AllPoints();
					for(String name : Serializer.allPointsLoad()){
						if(name.contains("/")){
							AllPoints.allPoints.put(name.split("/")[1], name);
						}else System.out.println("Not full name " + name);
					}
					if(allPoints == null){
						allPoints = new HashMap<String, String>();
						System.out.println("Still Null");
					}
				}
			}
		}
		return instance;
	}

	public void clearAllPoints() {
		allPoints.clear();
	}

	public String getPointFullName(String pointKey) {
		return AllPoints.allPoints.get(pointKey);
	}
	
	public IPoint getPoint(String pointKey)
	{
		String fullName;
		if(!allPoints.containsKey(pointKey))
			fullName = pointKey;
		else
			fullName = allPoints.get(pointKey);
		
		if(fullName == null)
			return null;
		
		String[] splitName = fullName.split("/");
		if(splitName.length != 2)
			return null;
		
		return AllMaps.getInstance().getMap(splitName[0]).getPoint(splitName[1]);
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

	public ArrayList<String> getAllPoints() {
		return new ArrayList<String>(allPoints.keySet());
	}
	

	public ArrayList<String> getAllPointsFull() {
		return new ArrayList<String>(allPoints.values());
	}

	public void save() {
		System.out.println("Saving: " +getAllPoints());
		Serializer.save(this);
		
	}

}
