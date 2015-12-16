package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import WPI.CampusMap.Recording.Serialization.Serializer;

public class AllMaps {
	public final String CampusMap = "Campus_Map";
	private static volatile AllMaps instance;
	private static HashMap<String, IMap> allMaps = new HashMap<String, IMap>();

	private AllMaps() {
	}

	/** Gets the instance of the singleton
	 * @return The instance of the singleton
	 */
	public static AllMaps getInstance() {
		if (instance == null) {
			synchronized (AllMaps.class) {
				if (instance == null) {
					instance = new AllMaps();
				}
			}
		}
		return instance;
	}

	/**
	 * Resets the instance to its initial state
	 */
	public void clearAllMaps() {
		allMaps.clear();
	}

	@Deprecated
	public HashMap<String, ? extends IMap> getAllMaps() {
		return allMaps;
	}

	/** Gets all the maps in given building
	 * @param building The building to get the maps in
	 * @return The maps in the specified building
	 */
	public ArrayList<IMap> getMapsInBuilding(String building) {
		ArrayList<IMap> temp = new ArrayList<IMap>();
		for (IMap map : allMaps.values()) {
			if (map.getBuilding().equals(building)) {
				temp.add(map);
			}
		}
		return temp;
	}

	/** Gets the map with the specified name
	 * @param mapKey The name of the desired map.
	 * @return The map with the given name.
	 */
	public IMap getMap(String mapKey) {
		if (allMaps.keySet().contains(mapKey)) {
			return AllMaps.allMaps.get(mapKey);
		} else {
			ProxyMap temp = Serializer.proxyLoad(mapKey);
			if (temp == null)
				temp = new ProxyMap(mapKey);
			addMap(temp);
			return temp;
		}
	}

	/** Adds a map to the instance
	 * @param mapValue The map to add.
	 * @return Weather the addition was successful.
	 */
	public boolean addMap(ProxyMap mapValue) {
		if ((AllMaps.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		AllMaps.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}

	/** Generates a whitelist of maps for pathifinding
	 * @param startMap The map with the start point.
	 * @param destinationMap The map with the goal point.
	 * @return the whitelist.
	 */
	public HashSet<String> generateWhitelist(String startMap, String destinationMap) {
//		System.out.println("Creating whitelist");
		HashSet<String> whitelist = new HashSet<String>();
		whitelist.add(startMap);
		String[] connectedToCampus = ((ProxyMap)getMap(CampusMap)).getConnectedMaps();
		
		//If same map
		if(startMap.equals(destinationMap)) {
			if(startMap.equals(CampusMap)){
				addConnectedToCampus(whitelist,connectedToCampus);
			}
			if(Arrays.asList(connectedToCampus).contains(startMap)){
				whitelist.add(CampusMap);
			}
//			System.out.println("Same map");
			return whitelist;
		}

		String startBuilding = startMap.split("-")[0];
		String destBuilding = destinationMap.split("-")[0];

		// If same building
		if (startBuilding.equals(destBuilding)) {
//			System.out.println("Same building");
			buildingDepthFirstSearch(startMap, destinationMap, new ArrayList<String>(), whitelist);
			return whitelist;
		}
		whitelist.add(CampusMap);
		addConnectedToCampus(whitelist,connectedToCampus);

		boolean startW = startMap.equals(CampusMap), destW = destinationMap.equals(CampusMap);
		for (String map : connectedToCampus) {
			// whitelist.add(CampusMap);
			String floorBuilding = map.split("-")[0];
			// System.out.println("Ground floor: "+floorBuilding);
			if (floorBuilding.equals(startBuilding)) {
				// System.out.println("Found ground floor of startbuilding:");
				startW = true;
				buildingDepthFirstSearch(startMap, map, new ArrayList<String>(), whitelist);
			} else if (floorBuilding.equals(destBuilding)) {
				// System.out.println("Found ground floor of destbuilding:");
				destW = true;
				buildingDepthFirstSearch(map, destinationMap, new ArrayList<String>(), whitelist);
			}
		}

		// if path found between campus map and both start and dest floors
		if (startW && destW) {
			// System.out.println("Returning full whitelist");
			return whitelist;
		}
		// no path found return empty list
		return new HashSet<String>();
	}
	
	private void addConnectedToCampus(HashSet<String> whitelist, String[] connectedToCampus){
		HashMap<String,String> addedMaps= new HashMap<String,String>();
		for(String map: connectedToCampus){
			String other = addedMaps.get(map);
			if(other == null){
				addedMaps.put(map.split("-")[0], map);
				whitelist.add(map);
			}else{
				buildingDepthFirstSearch(map, other, new ArrayList<String>(), whitelist);
			}
		}
	}

	private boolean buildingDepthFirstSearch(String current, String dest, ArrayList<String> stack,
			HashSet<String> whiteList) {

		String currentBuilding = current.split("-")[0];
		String destBuilding = dest.split("-")[0];

		if (!currentBuilding.equals(destBuilding)) {
			 System.out.println("Different buildings");
			return false;
		}

		stack = new ArrayList<String>(stack);
		stack.add(current);
		if (current.equals(dest)) {
//			 System.out.println("Found path:"+ current);
			whiteList.add(current);
			return true;
		}
		// System.out.println(((ProxyMap) getMap(current)).getConnectedMaps());
		// System.out.println(Arrays.toString(((ProxyMap)
		// getMap(current)).getConnectedMaps()));
		for (String neighbor : ((ProxyMap) getMap(current)).getConnectedMaps()) {
			String neighborBuilding = neighbor.split("-")[0];

			if (!stack.contains(neighbor) && neighborBuilding.equals(currentBuilding)) {
				if (buildingDepthFirstSearch(neighbor, dest, stack, whiteList)) {
					whiteList.add(current);
//					System.out.println(current);
					return true;
				}
			}

		}
		// System.out.println(Arrays.toString(((ProxyMap)
		// getMap(current)).getConnectedMaps()));
		return false;
	}

}
