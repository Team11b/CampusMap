package WPI.CampusMap.Recording.Serialization;

import WPI.CampusMap.Backend.Core.Map.ProxyMap;

public class Serializer {
	
	/**
	 * Saves the ProxyMap to a serial file
	 * Also saves the corresponding RealMap to a serial file
	 * @param tosave ProxyMap to save
	 */
	public static void save(ProxyMap tosave) {
		throw new UnsupportedOperationException("Save not yet implemented.");
	}
	
	/**
	 * Loads a ProxyMap from a serial file
	 * Adds the ProxyMap to the HashMap in AllMaps
	 * @param mapName name of the map to load
	 * @return a ProxyMap which represents mapName
	 */
	public static ProxyMap proxyLoad(String mapName) {
		throw new UnsupportedOperationException("Proxy load not yet implemented.");
	}
	
	/**
	 * Loads a RealMap from a serial file
	 * @param mapName name of the map to load
	 * @return a RealMap which represents mapName
	 */
	public static ProxyMap realLoad(String mapName) {
		throw new UnsupportedOperationException("Real load not yet implemented.");
	}

}
