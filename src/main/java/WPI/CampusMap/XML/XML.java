package WPI.CampusMap.XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Point;

public class XML {
	public static final String filePath = "./../../../XML/";
	public static final String fileEnd = ".xml";

	/**
	 * Static method which records information about the Points of a Map to an
	 * XML file. Uses template code from <a href=
	 * "http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm">
	 * http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm</a>
	 * 
	 * @param map
	 *            the map to record information from
	 * @param points
	 *            an array of Points to record information
	 * @see <a href=
	 *      "http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm">
	 *      http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
	 *      </a>
	 */
	public static void writePoints(Map map, Point[] points) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("Map");
			doc.appendChild(rootElement);
			
			rootElement.setAttribute("imageFile", map.getPng());
			rootElement.setAttribute("scale", Integer.toString(map.getScale()));
			
			for (int j = 0; j < points.length; j++) {
				rootElement.appendChild(new Node("Point"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
