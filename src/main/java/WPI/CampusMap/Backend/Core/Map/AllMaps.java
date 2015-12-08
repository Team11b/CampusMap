package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AllMaps {
    public final String CampusMap = "CampusMap";
    
	private static volatile AllMaps instance;
	private static HashMap<String, ProxyMap> allMaps = new HashMap<String, ProxyMap>();
	
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

	public IMap getMap(String mapKey) {
//		if (!(AllMaps.allMaps.containsKey(mapKey))) {
//			OLSSerializer.read(mapKey);
//		}
		return AllMaps.allMaps.get(mapKey);
	}

	public boolean addMap(ProxyMap mapValue) {
		if ((AllMaps.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		AllMaps.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}
	
	public HashSet<String> generateWhitelist(String startMap, String destinationMap){
		HashSet<String> whitelist = new HashSet<String>();
		whitelist.add(startMap);
		
		//If same map
		if(startMap.equals(destinationMap)) return whitelist;
		
		String startBuilding = startMap.split("-")[0];
		String destBuilding = destinationMap.split("-")[0];
		
		//If same building
		if(startBuilding.equals(destBuilding)){
			buildingDepthFirstSearch(startMap,destinationMap,new ArrayList<String>(), whitelist);
			return whitelist;
		}
		HashSet<String> connectedToCampus =((ProxyMap)getMap(CampusMap)).getConnectedMaps();
		whitelist.addAll(connectedToCampus);
		
		boolean startW = false,destW = false;
		for(String map: connectedToCampus){
			String floorBuilding = map.split("-")[0];
			if(floorBuilding.equals(startBuilding)){
				startW=true;
				buildingDepthFirstSearch(startMap,floorBuilding,new ArrayList<String>(), whitelist);
			}else if(floorBuilding.equals(destBuilding)){
				destW=true;
				buildingDepthFirstSearch(floorBuilding,destBuilding,new ArrayList<String>(), whitelist);
			}
		}
		
		//if path found between campus map and both start and dest floors
		if(startW && destW){
			return whitelist;
		}
		//no path found return empty list
		return new HashSet<String>();
	}
	
	private boolean buildingDepthFirstSearch(String current, String dest, 
											 ArrayList<String> visited, HashSet<String> whiteList){

		String currentBuilding = current.split("-")[0];
		String destBuilding = dest.split("-")[0];
		
		if(!currentBuilding.equals(destBuilding)) return false;
		
		visited.add(current);
		if(current.equals(dest)){
			whiteList.add(current);
			return true;
		}
		
		for(String neighbor: ((ProxyMap) getMap(current)).getConnectedMaps()){
			String neighborBuilding = neighbor.split("-")[0];
			
			if(!visited.contains(neighbor)&& neighborBuilding.equals(currentBuilding)){
				if(buildingDepthFirstSearch(neighbor, dest, visited, whiteList)){
					whiteList.add(current);
					return true;
				}
			}
			
		}
		return false;
	}
	
}
