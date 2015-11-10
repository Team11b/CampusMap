package WPI.CampusMap.AStarTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import WPI.CampusMap.AStar.Map;

public class XMLParseTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test() throws FileNotFoundException,XMLStreamException {
	Map testMap = new Map("","points.xml");
	System.out.print("");
	}

}
