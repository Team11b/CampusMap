package WPI.CampusMap.Recording.Serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import WPI.CampusMap.Backend.Core.Map.Map;

/**
 * 
 * @author Jacob Zizmor
 *
 */
@Deprecated
public class OLSSerializer {
	private static final String folder = "serialized/";
	private static final String fileType = ".ser";

	public static boolean write(Map amap) {
		try {
			FileOutputStream fileOut = new FileOutputStream(OLSSerializer.folder + amap.getName() + OLSSerializer.fileType);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(amap);

			out.close();
			fileOut.close();
			return true;
		} catch (IOException i) {
			System.out.println("NOT SAVED");
			i.printStackTrace();
			return false;
		}
	}

	public static Map read(String mapName) {
		try {
			Map m = null;
			FileInputStream fileIn = new FileInputStream(OLSSerializer.folder + mapName + OLSSerializer.fileType);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			m = (Map) in.readObject();

			in.close();
			fileIn.close();
			Map.addMap(m);
			m.fixMap();
			return m;
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("NOT LOADED");
			return null;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			System.out.println("NOT LOADED");
			return null;
		}
	}

}