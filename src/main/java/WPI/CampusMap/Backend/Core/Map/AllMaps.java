package WPI.CampusMap.Backend.Core.Map;

import java.util.HashMap;

import WPI.CampusMap.Recording.Serialization.OLSSerializer;

public class AllMaps {
	private static HashMap<String, IMap> allMaps = new HashMap<String, IMap>();
	
	public static void clearAllMaps() {
		AllMaps.allMaps.clear();
	}
	
	public static HashMap<String, IMap> getAllMaps() {
		return allMaps;
	}

	public static void setAllMaps(HashMap<String, IMap> allMaps) {
		AllMaps.allMaps = allMaps;
	}

	public static IMap getMap(String mapKey) {
//		if (!(AllMaps.allMaps.containsKey(mapKey))) {
//			OLSSerializer.read(mapKey);
//		}
		return AllMaps.allMaps.get(mapKey);
	}

	public static boolean addMap(IMap mapValue) {
		if ((AllMaps.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		AllMaps.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}
	
}
