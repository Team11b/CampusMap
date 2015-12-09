package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class AllMaps {
    public final String CampusMap = "Campus";
    
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
	public HashMap<String, IMap> getAllMaps() {
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
			ProxyMap temp = new ProxyMap(mapKey);
			addMap(temp);
			return temp;
		}
	}

	public boolean addMap(IMap mapValue) {
		if ((AllMaps.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		AllMaps.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}
	
}
