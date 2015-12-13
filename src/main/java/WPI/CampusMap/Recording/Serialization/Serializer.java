package WPI.CampusMap.Recording.Serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Map.RealMap;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Serializer {

	private static final String folder = "serialized/";
	public static final String folderProxy = Serializer.folder + "proxy/";
	public static final String folderReal = Serializer.folder + "real/";
	public static final String fileType = ".ser";

	/**
	 * Saves the ProxyMap to a serial file
	 * 
	 * @param tosave
	 *            ProxyMap to save
	 */
	public static void save(ProxyMap tosave) {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					Serializer.folderProxy + tosave.getName() + Serializer.fileType);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(tosave);
			System.out.println("Saved");

			out.close();
			fileOut.close();
		} catch (FileNotFoundException f) {
			System.out.println("NOT SAVED");
			JOptionPane.showMessageDialog(null, f.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
		} catch (IOException i) {
			System.out.println("NOT SAVED");
			JOptionPane.showMessageDialog(null, i.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			i.printStackTrace();
		}
	}

	/**
	 * Saves the RealMap to a serial file
	 * 
	 * @param tosave
	 *            RealMap to save
	 */
	public static void save(RealMap tosave) {
		try {
			FileOutputStream fileOut = new FileOutputStream(
					Serializer.folderReal + tosave.getName() + Serializer.fileType);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(tosave);

			out.close();
			fileOut.close();
		} catch (FileNotFoundException f) {
			System.out.println("NOT SAVED");
			JOptionPane.showMessageDialog(null, f.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
		} catch (IOException i) {
			System.out.println("NOT SAVED");
			JOptionPane.showMessageDialog(null, i.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			i.printStackTrace();
		}
	}

	/**
	 * Loads a ProxyMap from a serial file Adds the ProxyMap to the HashMap in
	 * AllMaps
	 * 
	 * @param mapName
	 *            name of the map to load
	 * @return a ProxyMap which represents mapName
	 */
	public static ProxyMap proxyLoad(String mapName) {
		try {
			ProxyMap pm;
			FileInputStream fileIn = new FileInputStream(Serializer.folderProxy + mapName + Serializer.fileType);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			pm = (ProxyMap) in.readObject();
			pm.onLoad();

			in.close();
			fileIn.close();

			// AllMaps.getInstance().clearAllMaps();
			return pm;
		} catch (FileNotFoundException f) {
			// System.out.println("NOT LOADED");
			// f.printStackTrace();
			JOptionPane.showMessageDialog(null, f.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException i) {
			System.out.println("NOT LOADED");
			JOptionPane.showMessageDialog(null, i.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("NOT LOADED");
			JOptionPane.showMessageDialog(null, c.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			c.printStackTrace();
		}

		return null;
	}

	/**
	 * Loads a RealMap from a serial file
	 * 
	 * @param mapName
	 *            name of the map to load
	 * @return a RealMap which represents mapName
	 */
	public static RealMap realLoad(String mapName) {
		try {
			RealMap rm;
			FileInputStream fileIn = new FileInputStream(Serializer.folderReal + mapName + Serializer.fileType);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			rm = (RealMap) in.readObject();

			in.close();
			fileIn.close();

			return rm;
		} catch (FileNotFoundException f) {
			// System.out.println("NOT LOADED");
			// f.printStackTrace();
			JOptionPane.showMessageDialog(null, f.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException i) {
			System.out.println("NOT LOADED");
			i.printStackTrace();
			JOptionPane.showMessageDialog(null, i.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException c) {
			System.out.println("NOT LOADED");
			c.printStackTrace();
			JOptionPane.showMessageDialog(null, c.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

}
