package WPI.CampusMap.Serialization;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.XML.XML;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Converter {
	public static String[] ignore = { "5x5Test.xml", "5x5Test2.xml", "AK.xml", "borked.xml", "testOutput.xml","borked.xml" };
	public static String[] allow = { "5x5Test.xml", "5x5TestCopy.xml" };
	
	public static String[] getFileNames() {
		File folder = new File("XML/");
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
					System.out.println(temp.getAllPoints().size());
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
			if ((Arrays.asList(Converter.allow).contains(files[j]))) {
				temp = new Map();
				temp.setXML("XML/" + files[j]);

				try {
					temp.setAllPoints(XML.parseXML(temp));
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
				System.out.println(temp.getName());
				HashMap<String, Point> holder = temp.getAllPoints();
				String[] keys = holder.keySet().toArray(new String[holder.keySet().size()]);
				
				for (int i = 0; i < keys.length; i++) {
					holder.get(keys[i]).setMap(temp.getName());
					holder.put(keys[i], new Point(holder.get(keys[i]).getCoord(), null, holder.get(keys[i]).getId(), temp.getName()));
				}

				if (temp.getName().equals("5x5Test")) {
					ArrayList<String> neigh = holder.get(connKey).getNeighborsID();
					ArrayList<Point> neighP = holder.get(connKey).getNeighborsP();
					System.out.println("" + neigh.size() + neighP.size());
					
					
					ConnectionPoint tempConnP =  new ConnectionPoint(holder.get(connKey).getCoord(), null, connKey,
							temp.getName(), "5x5TextCopy", connKey);
					
					for (int k = 0; k < neigh.size(); k++) {
						tempConnP.addNeighbor(neighP.get(k));
					}
				}
				else {
					ArrayList<String> neigh = holder.get(connKey).getNeighborsID();
					ArrayList<Point> neighP = holder.get(connKey).getNeighborsP();
					System.out.println("" + neigh.size() + neighP.size());
					
					ConnectionPoint tempConnP =  new ConnectionPoint(holder.get(connKey).getCoord(), null, connKey,
							temp.getName(), "5x5Text", connKey);
					
					for (int k = 0; k < neigh.size(); k++) {
						tempConnP.addNeighbor(neighP.get(k));
					}
				}
				temp.setAllPoints(holder);

				Serializer.write(temp);
			}
		}
	}
	
	public static void main(String[] args) {
		Converter.connectionTestPrep();
	}
}
