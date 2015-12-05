package WPI.CampusMap.Recording.Serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Map.RealMap;

public class Serializer {
	
	private static final String folder = "serialized/";
	private static final String folderProxy = Serializer.folder + "proxy/";
	private static final String folderReal = Serializer.folder + "real/";
	private static final String fileType = ".ser";
	
	/**
	 * Saves the ProxyMap to a serial file
	 * Also saves the corresponding RealMap to a serial file
	 * @param tosave ProxyMap to save
	 */
	public static void save(ProxyMap tosave) {
		
		// save proxy map
		try {
			FileOutputStream fileOut = new FileOutputStream(Serializer.folderProxy + tosave.getName() + Serializer.fileType);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(tosave);
			
			out.close();
			fileOut.close();
		}
		catch (FileNotFoundException f) {
			System.out.println("NOT SAVED");
			f.printStackTrace();
		}
		catch (IOException i) {
			System.out.println("NOT SAVED");
			i.printStackTrace();
		}
	}
	
	/**
	 * Loads a ProxyMap from a serial file
	 * Adds the ProxyMap to the HashMap in AllMaps
	 * @param mapName name of the map to load
	 * @return a ProxyMap which represents mapName
	 */
	public static ProxyMap proxyLoad(String mapName) {
		try {
			ProxyMap pm;
			FileInputStream fileIn = new FileInputStream(Serializer.folderProxy + mapName + Serializer.fileType);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			pm = (ProxyMap) in.readObject();
			
			in.close();
			fileIn.close();
			
			AllMaps.addMap(pm);
			return pm;
		}
		catch (FileNotFoundException f) {
			System.out.println("NOT LOADED");
			f.printStackTrace();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loads a RealMap from a serial file
	 * @param mapName name of the map to load
	 * @return a RealMap which represents mapName
	 */
	public static RealMap realLoad(String mapName) {
		throw new UnsupportedOperationException("Real load not yet implemented.");
	}

}
