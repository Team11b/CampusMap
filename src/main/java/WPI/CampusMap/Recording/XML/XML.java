package WPI.CampusMap.Recording.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.Core.Point.Point;

public class XML {
	public static final String filePath = "XML/";
	public static final String fileEnd = ".xml";

	public static PrintStream originalStream = System.out;
	public static OutputStream suppressedStream = new OutputStream() {
		@Override
		public void write(int b) {
			// NO-OP
		}
	};

	public static PrintStream dummyStream = new PrintStream(suppressedStream);

	/**
	 * Static method which records information about the Points of a Map to an
	 * XML file. Uses template code from <a href=
	 * "http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm">
	 * http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm</a>
	 * 
	 * @param map
	 *            the map to record information from
	 * @see <a href=
	 *      "http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm">
	 *      http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
	 *      </a>
	 */
	public static void writePoints(Map map) {
		try {
			System.setOut(dummyStream);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("Map");
			doc.appendChild(rootElement);

			rootElement.setAttribute("imageFile", map.getPng());
			rootElement.setAttribute("scale", Float.toString(map.getScale()));

			ArrayList<Point> sortedPoints = new ArrayList<Point>(map.getAllPoints().values());

			sortedPoints.sort(new Comparator<Point>() {
				public int compare(Point p1, Point p2) {
					return p1.getId().compareTo(p2.getId());
				}
			});

			Element newElement;
			Element subElement;
			for (int j = 0; j < sortedPoints.size(); j++) {
				newElement = doc.createElement("Node");
				rootElement.appendChild(newElement);
				newElement.setAttribute("nodeID", sortedPoints.get(j).getId());
				newElement.setAttribute("x", Double.toString(sortedPoints.get(j).getCoord().getX()));
				newElement.setAttribute("y", Double.toString(sortedPoints.get(j).getCoord().getY()));

				ArrayList<String> sortedConn = sortedPoints.get(j).getNeighborsID();
				sortedConn.sort(new Comparator<String>() {
					public int compare(String s1, String s2) {
						return s1.compareTo(s2);
					}
				});

				for (int k = 0; k < sortedConn.size(); k++) {
					subElement = doc.createElement("Connection");
					newElement.appendChild(subElement);
					subElement.appendChild(doc.createTextNode(sortedConn.get(k)));
				}
			}

			String file = XML.filePath;
			file = file.concat(map.getName());
			file = file.concat(XML.fileEnd);

			File xmlFile = new File(file);

			if (!xmlFile.exists()) {
				xmlFile.createNewFile();
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));
			transformer.transform(source, result);

			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
			System.setOut(originalStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to take an xml file as input and output an array of points.
	 * 
	 * @param map
	 *            Map to parse XML for
	 * @return Array of all points in the file
	 * @throws XMLStreamException
	 *             Thrown when XML files is improperly formated
	 */
	public static HashMap<String, Point> parseXML(Map map) throws XMLStreamException {
		Point currPoint = null;
		Coord tempCoord = null;
		String tagContent = null;

		HashMap<String, Point> pointMap = new HashMap<String, Point>();
		HashMap<String, ArrayList<String>> neighMap = new HashMap<String, ArrayList<String>>();
		@SuppressWarnings("unused")
		ArrayList<String> neighAList = new ArrayList<String>();

		XMLInputFactory factory = XMLInputFactory.newInstance();
		File testFile = new File(map.getXML());

		InputStream test = null;
		try {
			test = new FileInputStream(testFile);
		} catch (FileNotFoundException e) {
			return new HashMap<String, Point>();
		}

		XMLStreamReader reader = factory.createXMLStreamReader(test);

		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("Node".equals(reader.getLocalName())) {
					currPoint = new Point(map.getName());
					neighAList = new ArrayList<String>();
					currPoint.setId(reader.getAttributeValue(0));
					tempCoord = new Coord(Float.parseFloat(reader.getAttributeValue(1)),
							Float.parseFloat(reader.getAttributeValue(2)));
				}
				if ("Map".equals(reader.getLocalName())) {
					map.setName(map.getXML().substring(4, map.getXML().length() - 4));
					map.setPng(reader.getAttributeValue(0));
					map.setScale(Float.parseFloat(reader.getAttributeValue(1)));
				}
				break;
			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;
			case XMLStreamConstants.END_ELEMENT:

				switch (reader.getLocalName()) {
				case "Node":
					currPoint.setCoord(tempCoord);
					pointMap.put(currPoint.getId(), currPoint);
					if(neighMap.get(currPoint.getId()) == null) neighMap.put(currPoint.getId(), new ArrayList<String>());
//					neighMap.put(currPoint.getId(), neighAList);
					break;
				case "type":
					currPoint.setType(tagContent);
					break;
				case "Connection":
					ArrayList<String> temp = new ArrayList<String>();
					if (neighMap.containsKey(currPoint.getId())) {
						temp = neighMap.get(currPoint.getId());
					}
					temp.add(tagContent);
					neighMap.put(currPoint.getId(), temp);
					break;
				}
				break;
			}

		}

//		// goes through the points and gets the point objects associated with
//		// the neighbor ids and assigns them as neighbors
//		for (Point point : pointMap.values()) {
//			ArrayList<String> neighborIDs = neighMap.get(point.getId());
//			for (String neighbor : neighborIDs) {
//				pointMap.get(neighbor).addNeighbor(point);
//				neighMap.get(neighbor).remove(point);
//			}
//		}
		
		
		for (String s : pointMap.keySet()) {
			Point tempP = pointMap.get(s);
			ArrayList<String> aLS = neighMap.get(s);
//			System.out.println(s +": "+aLS);
			for (String sV : aLS) {
				tempP.addNeighbor(pointMap.get(sV));
			}
			pointMap.put(s, tempP);
		}

		return pointMap;
	}
}