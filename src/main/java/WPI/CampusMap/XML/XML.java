package WPI.CampusMap.XML;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class XML {
	public static final String filePath = "XML\\";
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
			Point[] points = map.getMap();
			System.setOut(dummyStream);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("Map");
			doc.appendChild(rootElement);

			rootElement.setAttribute("imageFile", map.getPng());
			rootElement.setAttribute("scale", Integer.toString(map.getScale()));

			ArrayList<Point> sortedPoints = new ArrayList<Point>(Arrays.asList(points));
			Collections.sort(sortedPoints, new Comparator<Point>() {
				public int compare(Point p1, Point p2) {
					return p1.getId().compareTo(p2.getId());
				}
			});

			Element newElement;
			Element subElement;
			for (int j = 0; j < points.length; j++) {
				newElement = doc.createElement("Point");
				rootElement.appendChild(newElement);
				newElement.setAttribute("nodeID", sortedPoints.get(j).getId());
				newElement.setAttribute("x", Double.toString(sortedPoints.get(j).getCoord().getX()));
				newElement.setAttribute("y", Double.toString(sortedPoints.get(j).getCoord().getY()));

				ArrayList<String> sortedConn = new ArrayList<String>(
						Arrays.asList(sortedPoints.get(j).getNeighborsID()));
				Collections.sort(sortedConn, new Comparator<String>() {
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
}
