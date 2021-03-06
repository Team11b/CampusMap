package WPI.CampusMap.Backend.Core.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class AllPoints implements Serializable{


	private static final long serialVersionUID = -6405416499845660178L;
	private transient static volatile AllPoints instance;
	//Contains only named points, key is the name of the point and value is the fullName
	private static HashMap<String, String> allPoints = new HashMap<String, String>();

	private AllPoints() {
	}

	/** Gets the instance of the singleton
	 * @return The instance of the singleton
	 */
	public static AllPoints getInstance() {
		if (instance == null) {
			synchronized (AllPoints.class) {
				if (instance == null) {
					instance = new AllPoints();
					LinkedList<String> temp = Serializer.allPointsLoad(); 
					if(temp != null){
						for(String point: temp){
							allPoints.put(point.split("/")[1], point);
						}
					}
				}
			}
		}
		return instance;
	}
	
	/**
	 * Resets the instance to its initial state
	 */
	public void clearAllPoints() {
		allPoints.clear();
	}

	/** Gets the full name of the specified name
	 * @param pointKey The shorthand name of the point
	 * @return Fullname of the point
	 */
	public String getPointFullName(String pointKey) {
		return AllPoints.allPoints.get(pointKey);
	}
	
	/** Gets the point from the shorthand name of the point
	 * @param pointKey The shortHand name of the point.
	 * @return The point.
	 */
	public IPoint getPoint(String pointKey)
	{
		String fullName;
		if(!allPoints.containsKey(pointKey))
			fullName = pointKey;
		else
			fullName = allPoints.get(pointKey);
		
		if(fullName == null){
			System.out.println("Null Name");
			return null;
		}
		
		String[] splitName = fullName.split("/");
		if(splitName.length != 2){
			System.out.println("Full Name improperly formated");
			return null;
		}
//		System.out.println(fullName);
		return AllMaps.getInstance().getMap(splitName[0]).getPoint(splitName[1]);
	}

	
	/** Add a point to the instance.
	 * @param pointFullName The full name of the point.
	 * @return If the addition was successful.
	 */
	public boolean addPoint(String pointFullName) {
		String pointName = pointFullName.split("/")[1];
		if ((AllPoints.allPoints.containsKey(pointName))) {
				return false;
		}

		AllPoints.allPoints.put(pointName, pointFullName);
		return true;
	}

	/** Adds all the points in an ArrayList.
	 * @param multiPoints The ArrayList of Points
	 * @return If the addition was successful.
	 */
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

	/** Adds all the points in an Array.
	 * @param multiPoints The Array of Points
	 * @return If the addition was successful.
	 */
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
	
	/** Gets short names of all of the points
	 * @return An ArrayList of all the points.
	 */
	public ArrayList<String> getAllPointsShortName(){
		return new ArrayList<String>(allPoints.keySet());
	}

	public ArrayList<String> getAllPoints() {
		return new ArrayList<String>(allPoints.keySet());
	}
	

	public ArrayList<String> getAllPointsFull() {
		return new ArrayList<String>(allPoints.values());
	}


	public void save() {
		for(String point : allPoints.values()){
			if(getPoint(point)== null ||!getPoint(point).exists()){
				System.out.println("Point " + point + "does not exist, removing from allPoints.");
				allPoints.remove(point.split("/")[0]);
			}
		}
		
		Serializer.save(new LinkedList<String>(allPoints.values()));
		
	}

}
