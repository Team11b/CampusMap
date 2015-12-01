package WPI.CampusMap.Backend.Serialization;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Core.Map;
import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.XML.XML;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Converter {
	public static String[] ignore = { "5x5Test.xml", "5x5Test2.xml", "AK.xml", "borked.xml", "testOutput.xml",
			"borked.xml" };
	public static String[] allow = { "5x5Test.ser", "5x5Test2.ser", "5x5TestCopy.ser" };
	public static String[] allowXML = { "5x5Test.xml", "5x5Test2.xml", "5x5TestCopy.xml" };

	public static String[] getFileNames() {
		File folder = new File("XML/");
		File[] listOfFiles = folder.listFiles();
		String[] listOfNames = new String[listOfFiles.length];

		for (int j = 0; j < listOfFiles.length; j++) {
			listOfNames[j] = listOfFiles[j].getName();
		}

		return listOfNames;
	}

	public static String[] getSerNames() {
		File folder = new File("serialized/");
		File[] listOfFiles = folder.listFiles();
		String[] listOfNames = new String[listOfFiles.length];

		for (int j = 0; j < listOfFiles.length; j++) {
			listOfNames[j] = listOfFiles[j].getName();
		}

		return listOfNames;
	}

	public static void run() {
		Map temp = new Map();
		String[] files = Converter.getFileNames();

		for (int j = 0; j < files.length; j++) {
			if (!(Arrays.asList(Converter.ignore).contains(files[j]))) {
				temp = new Map();
				temp.setXML("XML/" + files[j]);

				try {
					temp.setAllPoints(XML.parseXML(temp));
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
				System.out.println(temp.getName());

				Serializer.write(temp);
			}
		}
	}

	public static void connectionTestPrep() {
		Map temp = new Map();
		String[] files = Converter.getFileNames();
		String connKey = "4";

		for (int j = 0; j < files.length; j++) {
			if ((Arrays.asList(Converter.allowXML).contains(files[j]))) {
				System.out.println(files[j]);
				temp = new Map();
				temp.setXML("XML/" + files[j]);

				try {
					HashMap<String, Point> hello = XML.parseXML(temp);
					temp.setAllPoints(hello);
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
				HashMap<String, Point> holder = temp.getAllPoints();
				String[] keys = holder.keySet().toArray(new String[holder.keySet().size()]);

//				for (int i = 0; i < keys.length; i++) {
//					holder.get(keys[i]).setMap(temp.getName());
//					holder.put(keys[i], new Point(holder.get(keys[i]).getCoord(), null, holder.get(keys[i]).getId(),
//							temp.getName()));
//				}

				temp.setAllPoints(holder);

				Serializer.write(temp);
			}
		}
	}

	public static void addMapFromSer() {
		Map temp = new Map();
		String[] files = Converter.getSerNames();

		for (int j = 0; j < files.length; j++) {
			String file = files[j].substring(0, files[j].length() - 4) + ".ser";
			if (Converter.contains(file, Converter.allow)) {
				temp = Serializer.read(files[j].substring(0, files[j].length() - 4));
				System.out.println(temp.getName());
				temp.setAllPointMaps();
				Serializer.write(temp);
			}
		}
		System.out.println("done");
	}
	
	private static boolean contains(String find, String[] list) {
		for (String s : list) {
			if (s.equals(find)) {
				return true;
			}
		}
		return false;
	}
}
