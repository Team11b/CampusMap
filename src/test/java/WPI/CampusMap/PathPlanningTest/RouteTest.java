package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar.AStar;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Route.Route;
import WPI.CampusMap.Recording.Serialization.OLSSerializer;

public class RouteTest {
	static Map testMap5, testMap6;

	@BeforeClass
	public static void getMap() throws XMLStreamException {

		setupMaps();
	}

	private static void setupMaps() {
		String a, b;
		a = "MapA";
		b = "MapB";

		testMap5 = new Map(a);
		testMap5.setScale(1);

		Point zero = new Point(new Coord(0, 0), "", "0", a);
		Point one = new Point(new Coord(1, 0), "", "1", a);
		Point two = new Point(new Coord(2, 0), "", "2", a);
		Point three = new Point(new Coord(3, 0), "", "3", a);
		ConnectionPoint four = new ConnectionPoint(new Coord(4, 0), "", "4", a, b, "4");
		Point five = new Point(new Coord(0, 1), "", "5", a);
		Point six = new Point(new Coord(1, 1), "", "6", a);
		Point seven = new Point(new Coord(2, 1), "", "7", a);
		Point eight = new Point(new Coord(3, 1), "", "8", a);
		Point nine = new Point(new Coord(4, 1), "", "9", a);
		Point ten = new Point(new Coord(0, 2), "", "10", a);
		Point eleven = new Point(new Coord(1, 2), "", "11", a);
		Point twelve = new Point(new Coord(2, 2), "", "12", a);
		Point fourteen = new Point(new Coord(4, 2), "", "14", a);
		Point fifteen = new Point(new Coord(0, 3), "", "15", a);
		Point sixteen = new Point(new Coord(1, 3), "", "16", a);
		Point seventeen = new Point(new Coord(2, 3), "", "17", a);
		Point eightteen = new Point(new Coord(3, 3), "", "18", a);
		Point nineteen = new Point(new Coord(4, 3), "", "19", a);
		Point twenty = new Point(new Coord(0, 4), "", "20", a);
		Point twentyone = new Point(new Coord(1, 4), "", "21", a);
		Point twentytwo = new Point(new Coord(2, 4), "", "22", a);
		Point twentythree = new Point(new Coord(3, 4), "", "23", a);
		Point twentyfour = new Point(new Coord(4, 4), "", "24", a);

		zero.addNeighbor(one);
		zero.addNeighbor(five);

		one.addNeighbor(zero);
		one.addNeighbor(six);
		one.addNeighbor(two);

		two.addNeighbor(one);
		two.addNeighbor(three);
		two.addNeighbor(seven);

		three.addNeighbor(two);
		three.addNeighbor(four);
		three.addNeighbor(eight);

		four.addNeighbor(nine);
		four.addNeighbor(three);

		five.addNeighbor(zero);
		five.addNeighbor(six);
		five.addNeighbor(ten);

		six.addNeighbor(one);
		six.addNeighbor(five);
		six.addNeighbor(eleven);
		six.addNeighbor(seven);

		seven.addNeighbor(two);
		seven.addNeighbor(six);
		seven.addNeighbor(eight);
		seven.addNeighbor(twelve);

		eight.addNeighbor(three);
		eight.addNeighbor(seven);
		eight.addNeighbor(nine);

		nine.addNeighbor(four);
		nine.addNeighbor(eight);
		nine.addNeighbor(fourteen);

		ten.addNeighbor(five);
		ten.addNeighbor(eleven);
		ten.addNeighbor(fifteen);

		eleven.addNeighbor(six);
		eleven.addNeighbor(ten);
		eleven.addNeighbor(twelve);
		eleven.addNeighbor(sixteen);

		twelve.addNeighbor(seven);
		twelve.addNeighbor(eleven);
		twelve.addNeighbor(seventeen);

		fourteen.addNeighbor(nine);
		fourteen.addNeighbor(nineteen);

		fifteen.addNeighbor(ten);
		fifteen.addNeighbor(twenty);
		fifteen.addNeighbor(sixteen);

		sixteen.addNeighbor(fifteen);
		sixteen.addNeighbor(seventeen);
		sixteen.addNeighbor(eleven);
		sixteen.addNeighbor(twentyone);

		seventeen.addNeighbor(twelve);
		seventeen.addNeighbor(sixteen);
		seventeen.addNeighbor(eightteen);
		seventeen.addNeighbor(twentytwo);

		eightteen.addNeighbor(seventeen);
		eightteen.addNeighbor(nineteen);
		eightteen.addNeighbor(twentythree);

		nineteen.addNeighbor(fourteen);
		nineteen.addNeighbor(twentyfour);
		nineteen.addNeighbor(eightteen);

		twenty.addNeighbor(fifteen);
		twenty.addNeighbor(twentyone);

		twentyone.addNeighbor(twenty);
		twentyone.addNeighbor(sixteen);
		twentyone.addNeighbor(twentytwo);

		twentytwo.addNeighbor(twentyone);
		twentytwo.addNeighbor(twentythree);
		twentytwo.addNeighbor(seventeen);

		twentythree.addNeighbor(eightteen);
		twentythree.addNeighbor(twentytwo);
		twentythree.addNeighbor(twentyfour);

		twentyfour.addNeighbor(twentythree);
		twentyfour.addNeighbor(nineteen);

		Point[] all = { zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, fourteen,
				fifteen, sixteen, seventeen, eightteen, nineteen, twenty, twentyone, twentytwo, twentythree,
				twentyfour };
		HashMap<String, Point> allHM = new HashMap<String, Point>();

		for (int k = 0; k < all.length; k++) {
			allHM.put(all[k].getId(), all[k]);
		}

		testMap5.setAllPoints(allHM);

		testMap6 = new Map(b);
		testMap6.setScale(100);

		Point zero2 = new Point(new Coord(0, 0), "", "0", b);
		Point one2 = new Point(new Coord(1, 0), "", "1", b);
		Point two2 = new Point(new Coord(2, 0), "", "2", b);
		Point three2 = new Point(new Coord(3, 0), "", "3", b);
		ConnectionPoint four2 = new ConnectionPoint(new Coord(4, 0), "", "4", b, a, "4");
		Point five2 = new Point(new Coord(0, 1), "", "5", b);
		Point six2 = new Point(new Coord(1, 1), "", "6", b);
		Point seven2 = new Point(new Coord(2, 1), "", "7", b);
		Point eight2 = new Point(new Coord(3, 1), "", "8", b);
		Point nine2 = new Point(new Coord(4, 1), "", "9", b);
		Point ten2 = new Point(new Coord(0, 2), "", "10", b);
		Point eleven2 = new Point(new Coord(1, 2), "", "11", b);
		Point twelve2 = new Point(new Coord(2, 2), "", "12", b);
		Point fourteen2 = new Point(new Coord(4, 2), "", "14", b);
		Point fifteen2 = new Point(new Coord(0, 3), "", "15", b);
		Point sixteen2 = new Point(new Coord(1, 3), "", "16", b);
		Point seventeen2 = new Point(new Coord(2, 3), "", "17", b);
		Point eightteen2 = new Point(new Coord(3, 3), "", "18", b);
		Point nineteen2 = new Point(new Coord(4, 3), "", "19", b);
		Point twenty2 = new Point(new Coord(0, 4), "", "20", b);
		Point twentyone2 = new Point(new Coord(1, 4), "", "21", b);
		Point twentytwo2 = new Point(new Coord(2, 4), "", "22", b);
		Point twentythree2 = new Point(new Coord(3, 4), "", "23", b);
		Point twentyfour2 = new Point(new Coord(4, 4), "", "24", b);

		zero2.addNeighbor(one2);
		zero2.addNeighbor(five2);

		one2.addNeighbor(zero2);
		one2.addNeighbor(six2);
		one2.addNeighbor(two2);

		two2.addNeighbor(one2);
		two2.addNeighbor(three2);
		two2.addNeighbor(seven2);

		three2.addNeighbor(two2);
		three2.addNeighbor(four2);
		three2.addNeighbor(eight2);

		four2.addNeighbor(nine2);
		four2.addNeighbor(three2);

		five2.addNeighbor(zero2);
		five2.addNeighbor(six2);
		five2.addNeighbor(ten2);

		six2.addNeighbor(one2);
		six2.addNeighbor(five2);
		six2.addNeighbor(eleven2);
		six2.addNeighbor(seven2);

		seven2.addNeighbor(two2);
		seven2.addNeighbor(six2);
		seven2.addNeighbor(eight2);
		seven2.addNeighbor(twelve2);

		eight2.addNeighbor(three2);
		eight2.addNeighbor(seven2);
		eight2.addNeighbor(nine2);

		nine2.addNeighbor(four2);
		nine2.addNeighbor(eight2);
		nine2.addNeighbor(fourteen2);

		ten2.addNeighbor(five2);
		ten2.addNeighbor(eleven2);
		ten2.addNeighbor(fifteen2);

		eleven2.addNeighbor(six2);
		eleven2.addNeighbor(ten2);
		eleven2.addNeighbor(twelve2);
		eleven2.addNeighbor(sixteen2);

		twelve2.addNeighbor(seven2);
		twelve2.addNeighbor(eleven2);
		twelve2.addNeighbor(seventeen2);

		fourteen2.addNeighbor(nine2);
		fourteen2.addNeighbor(nineteen2);

		fifteen2.addNeighbor(ten2);
		fifteen2.addNeighbor(twenty2);
		fifteen2.addNeighbor(sixteen2);

		sixteen2.addNeighbor(fifteen2);
		sixteen2.addNeighbor(seventeen2);
		sixteen2.addNeighbor(eleven2);
		sixteen2.addNeighbor(twentyone2);

		seventeen2.addNeighbor(twelve2);
		seventeen2.addNeighbor(sixteen2);
		seventeen2.addNeighbor(eightteen2);
		seventeen2.addNeighbor(twentytwo2);

		eightteen2.addNeighbor(seventeen2);
		eightteen2.addNeighbor(nineteen2);
		eightteen2.addNeighbor(twentythree2);

		nineteen2.addNeighbor(fourteen2);
		nineteen2.addNeighbor(twentyfour2);
		nineteen2.addNeighbor(eightteen2);

		twenty2.addNeighbor(fifteen2);
		twenty2.addNeighbor(twentyone2);

		twentyone2.addNeighbor(twenty2);
		twentyone2.addNeighbor(sixteen2);
		twentyone2.addNeighbor(twentytwo2);

		twentytwo2.addNeighbor(twentyone2);
		twentytwo2.addNeighbor(twentythree2);
		twentytwo2.addNeighbor(seventeen2);

		twentythree2.addNeighbor(eightteen2);
		twentythree2.addNeighbor(twentytwo2);
		twentythree2.addNeighbor(twentyfour2);

		twentyfour2.addNeighbor(twentythree2);
		twentyfour2.addNeighbor(nineteen2);

		Point[] all2 = { zero2, one2, two2, three2, four2, five2, six2, seven2, eight2, nine2, ten2, eleven2, twelve2,
				fourteen2, fifteen2, sixteen2, seventeen2, eightteen2, nineteen2, twenty2, twentyone2, twentytwo2,
				twentythree2, twentyfour2 };
		HashMap<String, Point> allHM2 = new HashMap<String, Point>();

		for (int k = 0; k < all2.length; k++) {
			allHM2.put(all2[k].getId(), all2[k]);
		}

		testMap6.setAllPoints(allHM2);

		OLSSerializer.write(testMap5);
		OLSSerializer.write(testMap6);
	}

	@Ignore
	@Test
	public void testConnected() {
		Map.addMap(testMap5);
		Point start, goal;
		start = testMap5.getPoint("0");
		goal = testMap6.getPoint("12");

		MultiPath grandPath = AStar.multi_AStar(start, goal);
		ArrayList<Node> pathNodes = grandPath.get(0).getPath();
		assertEquals(5, pathNodes.size());
		assertEquals(pathNodes.get(0).getPoint().getId(), "0");
		assertEquals(pathNodes.get(1).getPoint().getId(), "1");
		assertEquals(pathNodes.get(2).getPoint().getId(), "2");
		assertEquals(pathNodes.get(3).getPoint().getId(), "3");
		assertEquals(pathNodes.get(4).getPoint().getId(), "4");

		pathNodes = grandPath.get(1).getPath();
		assertEquals(5, pathNodes.size());
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "9");
		assertEquals(pathNodes.get(2).getPoint().getId(), "8");
		assertEquals(pathNodes.get(3).getPoint().getId(), "7");
		assertEquals(pathNodes.get(4).getPoint().getId(), "12");
		
		Route ar = new Route(grandPath);
		System.out.println(ar);
	}

}