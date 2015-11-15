package WPI.CampusMap.XMLTest;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.XML.XML;

public class XMLTest {
	
	@Test
	public void testWriteXML() throws FileNotFoundException, XMLStreamException {
		Map map = new Map("XML/AK.xml");
		XML.writePoints(map, map.getMap());
	}
	
}
