package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import WPI.CampusMap.Recording.Serialization.Serializer;

public class AllMaps {
    public final String CampusMap = "CampusMap";
    
	private static volatile AllMaps instance;
	private static HashMap<String, IMap> allMaps = new HashMap<String, IMap>();
	
	private AllMaps(){}
	
	public static AllMaps getInstance(){
		if(instance == null){
            synchronized (AllMaps.class){
            	if(instance == null){
        			instance = new AllMaps();
            	}
            }
		}
		return instance;
	}
	
	public void clearAllMaps() {
		allMaps.clear();
	}
	
	@Deprecated
	public HashMap<String, ? extends IMap> getAllMaps() {
		return allMaps;
	}

	public void setAllMaps(HashMap<String, IMap> allMaps) {
		AllMaps.allMaps = allMaps;
	}
	
	public ArrayList<IMap> getMapsInBuilding(String building){
		ArrayList<IMap> temp = new ArrayList<IMap>();
		for(IMap map:allMaps.values()){
			if(map.getBuilding().equals(building)){
				temp.add(map);
			}
		}
		return temp;
	}

	public IMap getMap(String mapKey) {
		if(allMaps.keySet().contains(mapKey)){
			return AllMaps.allMaps.get(mapKey);
		}else{
			ProxyMap temp = Serializer.proxyLoad(mapKey);
			if(temp == null)temp = new ProxyMap(mapKey);
			addMap(temp);
			return temp;
		}
	}

	public boolean addMap(ProxyMap mapValue) {
		if ((AllMaps.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		AllMaps.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}
	
	public HashSet<String> generateWhitelist(String startMap, String destinationMap){
		System.out.println("Creating whitelist");
		HashSet<String> whitelist = new HashSet<String>();
//		whitelist.add(startMap);
		
		//If same map
		if(startMap.equals(destinationMap)) {
//			System.out.println("Same map");
			return whitelist;
		}
		
		String startBuilding = startMap.split("-")[0];
		String destBuilding = destinationMap.split("-")[0];
		
		//If same building
		if(startBuilding.equals(destBuilding)){
//			System.out.println("Same building");
			buildingDepthFirstSearch(startMap,destinationMap,new ArrayList<String>(), whitelist);
			return whitelist;
		}
		String[] connectedToCampus = ((ProxyMap)getMap(CampusMap)).getConnectedMaps();
//		System.out.println("Adding campus connected maps");
		whitelist.add(CampusMap);
		for(String map: connectedToCampus){
			whitelist.add(map);
		}
		
		boolean startW = startMap.equals(CampusMap), destW = destinationMap.equals(CampusMap);
		for(String map: connectedToCampus){
//			whitelist.add(CampusMap);
			String floorBuilding = map.split("-")[0];
//			System.out.println("Ground floor: "+floorBuilding);
			if(floorBuilding.equals(startBuilding)){
//				System.out.println("Found ground floor of startbuilding:");
				startW=true;
				buildingDepthFirstSearch(startMap,map,new ArrayList<String>(), whitelist);
			}else if(floorBuilding.equals(destBuilding)){
//				System.out.println("Found ground floor of destbuilding:");
				destW=true;
				buildingDepthFirstSearch(map,destinationMap,new ArrayList<String>(), whitelist);
			}
		}
		
		//if path found between campus map and both start and dest floors
		if(startW && destW){
//			System.out.println("Returning full whitelist");
			return whitelist;
		}
		//no path found return empty list
		return new HashSet<String>();
	}
	
	private boolean buildingDepthFirstSearch(String current, String dest, 
											 ArrayList<String> visited, HashSet<String> whiteList){

		String currentBuilding = current.split("-")[0];
		String destBuilding = dest.split("-")[0];
		
		if(!currentBuilding.equals(destBuilding)){
//			System.out.println("Different buildings");
			return false;
		}
		
		visited.add(current);
		if(current.equals(dest)){
//			System.out.println("Found path:"+ current);
			whiteList.add(current);
			return true;
		}
//		System.out.println(((ProxyMap) getMap(current)).getConnectedMaps());
		System.out.println(Arrays.toString(((ProxyMap) getMap(current)).getConnectedMaps()));
		for(String neighbor: ((ProxyMap) getMap(current)).getConnectedMaps()){
			String neighborBuilding = neighbor.split("-")[0];
			
			if(!visited.contains(neighbor)&& neighborBuilding.equals(currentBuilding)){
				if(buildingDepthFirstSearch(neighbor, dest, visited, whiteList)){
					whiteList.add(current);
//					System.out.println(current);
					return true;
				}
			}
			
		}
//		System.out.println(Arrays.toString(((ProxyMap) getMap(current)).getConnectedMaps()));
		return false;
	}
	
}
