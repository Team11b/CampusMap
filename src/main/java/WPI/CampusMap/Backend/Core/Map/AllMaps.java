package WPI.CampusMap.Backend.Core.Map;

import java.util.HashMap;

public class AllMaps {
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

	public IMap getMap(String mapKey) {
//		if (!(AllMaps.allMaps.containsKey(mapKey))) {
//			OLSSerializer.read(mapKey);
//		}
		return AllMaps.allMaps.get(mapKey);
	}

	public boolean addMap(IMap mapValue) {
		if ((AllMaps.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		AllMaps.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}
	
}
