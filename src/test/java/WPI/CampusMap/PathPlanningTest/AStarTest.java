package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.Path;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar.AStar;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;
import WPI.CampusMap.Recording.Serialization.Serializer;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class AStarTest {
	static ProxyMap testMap, testMap2, testMap5, testMap6;

	@BeforeClass
	public static void getMap() throws XMLStreamException {
		AllMaps.getInstance().clearAllMaps();
		
		testMap = Serializer.proxyLoad("5x5Test");
		testMap2 = Serializer.proxyLoad("5x5Test2");

		setupMaps();
	}

	private static void setupMaps() {
		String a, b;
		a = "MapA";
		b = "MapB";

		testMap5 = new ProxyMap(a);
		testMap5.setScale(100);

		RealPoint zero = new RealPoint(new Coord(0, 0), "", "0", a);
		RealPoint one = new RealPoint(new Coord(1, 0), "", "1", a);
		RealPoint two = new RealPoint(new Coord(2, 0), "", "2", a);
		RealPoint three = new RealPoint(new Coord(3, 0), "", "3", a);
		ConnectionPoint four = new ConnectionPoint(new Coord(4, 0), "", "4", a, b, "4");
		RealPoint five = new RealPoint(new Coord(0, 1), "", "5", a);
		RealPoint six = new RealPoint(new Coord(1, 1), "", "6", a);
		RealPoint seven = new RealPoint(new Coord(2, 1), "", "7", a);
		RealPoint eight = new RealPoint(new Coord(3, 1), "", "8", a);
		RealPoint nine = new RealPoint(new Coord(4, 1), "", "9", a);
		RealPoint ten = new RealPoint(new Coord(0, 2), "", "10", a);
		RealPoint eleven = new RealPoint(new Coord(1, 2), "", "11", a);
		RealPoint twelve = new RealPoint(new Coord(2, 2), "", "12", a);
		RealPoint fourteen = new RealPoint(new Coord(4, 2), "", "14", a);
		RealPoint fifteen = new RealPoint(new Coord(0, 3), "", "15", a);
		RealPoint sixteen = new RealPoint(new Coord(1, 3), "", "16", a);
		RealPoint seventeen = new RealPoint(new Coord(2, 3), "", "17", a);
		RealPoint eightteen = new RealPoint(new Coord(3, 3), "", "18", a);
		RealPoint nineteen = new RealPoint(new Coord(4, 3), "", "19", a);
		RealPoint twenty = new RealPoint(new Coord(0, 4), "", "20", a);
		RealPoint twentyone = new RealPoint(new Coord(1, 4), "", "21", a);
		RealPoint twentytwo = new RealPoint(new Coord(2, 4), "", "22", a);
		RealPoint twentythree = new RealPoint(new Coord(3, 4), "", "23", a);
		RealPoint twentyfour = new RealPoint(new Coord(4, 4), "", "24", a);

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

		RealPoint[] all = { zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, fourteen,
				fifteen, sixteen, seventeen, eightteen, nineteen, twenty, twentyone, twentytwo, twentythree,
				twentyfour };
		HashMap<String, RealPoint> allHM = new HashMap<String, RealPoint>();

		for (int k = 0; k < all.length; k++) {
			allHM.put(all[k].getId(), all[k]);
		}

		testMap5.setAllPoints(allHM);

		testMap6 = new RealMap(b);
		testMap6.setScale(100);

		RealPoint zero2 = new RealPoint(new Coord(0, 0), "", "0", b);
		RealPoint one2 = new RealPoint(new Coord(1, 0), "", "1", b);
		RealPoint two2 = new RealPoint(new Coord(2, 0), "", "2", b);
		RealPoint three2 = new RealPoint(new Coord(3, 0), "", "3", b);
		RealPoint four2 = new RealPoint(new Coord(4, 0), "", "4", b, a, "4");
		RealPoint five2 = new RealPoint(new Coord(5, 0), "", "5", b);
		RealPoint six2 = new RealPoint(new Coord(6, 0), "", "6", b);
		RealPoint seven2 = new RealPoint(new Coord(7, 0), "", "7", b);
		RealPoint eight2 = new RealPoint(new Coord(8, 0), "", "8", b);
		RealPoint nine2 = new RealPoint(new Coord(9, 0), "", "9", b);
		RealPoint ten2 = new RealPoint(new Coord(10, 0), "", "10", b);
		RealPoint eleven2 = new RealPoint(new Coord(11, 0), "", "11", b);
		RealPoint twelve2 = new RealPoint(new Coord(12, 0), "", "12", b);
		RealPoint fourteen2 = new RealPoint(new Coord(14, 0), "", "14", b);
		RealPoint fifteen2 = new RealPoint(new Coord(15, 0), "", "15", b);
		RealPoint sixteen2 = new RealPoint(new Coord(16, 0), "", "16", b);
		RealPoint seventeen2 = new RealPoint(new Coord(17, 0), "", "17", b);
		RealPoint eightteen2 = new RealPoint(new Coord(18, 0), "", "18", b);
		RealPoint nineteen2 = new RealPoint(new Coord(19, 0), "", "19", b);
		RealPoint twenty2 = new RealPoint(new Coord(20, 0), "", "20", b);
		RealPoint twentyone2 = new RealPoint(new Coord(21, 0), "", "21", b);
		RealPoint twentytwo2 = new RealPoint(new Coord(22, 0), "", "22", b);
		RealPoint twentythree2 = new RealPoint(new Coord(23, 0), "", "23", b);
		RealPoint twentyfour2 = new RealPoint(new Coord(24, 0), "", "24", b);

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

		RealPoint[] all2 = { zero2, one2, two2, three2, four2, five2, six2, seven2, eight2, nine2, ten2, eleven2, twelve2,
				fourteen2, fifteen2, sixteen2, seventeen2, eightteen2, nineteen2, twenty2, twentyone2, twentytwo2,
				twentythree2, twentyfour2 };
		HashMap<String, RealPoint> allHM2 = new HashMap<String, RealPoint>();

		for (int k = 0; k < all2.length; k++) {
			allHM2.put(all2[k].getId(), all2[k]);
		}

//		testMap6.setAllPoints(allHM2);

		Serializer.save(testMap5);
		Serializer.save(testMap6);
	}
	@Ignore
	@Test
	public void testAStar4to12() {
		RealPoint start, goal;
		start = testMap.getPoint("4");
		goal = testMap.getPoint("12");

		Path path = AStar.multi_AStar(start, goal).get(0);
		ArrayList<Node> pathNodes = path.getPath();
		assertEquals(7, path.getPath().size());
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "9");
		assertEquals(pathNodes.get(2).getPoint().getId(), "14");
		assertEquals(pathNodes.get(3).getPoint().getId(), "19");
		assertEquals(pathNodes.get(4).getPoint().getId(), "18");
		assertEquals(pathNodes.get(5).getPoint().getId(), "17");
		assertEquals(pathNodes.get(6).getPoint().getId(), "12");
	}
	@Ignore
	@Test
	public void testAStar4to11() {
		RealPoint start, goal;
		start = testMap.getPoint("4");
		goal = testMap.getPoint("11");

		Path path = AStar.multi_AStar(start, goal).get(0);
		ArrayList<Node> pathNodes = path.getPath();

		assertEquals(path.getPath().size(), 8);
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "3");
		assertEquals(pathNodes.get(2).getPoint().getId(), "2");
		assertEquals(pathNodes.get(3).getPoint().getId(), "1");
		assertEquals(pathNodes.get(4).getPoint().getId(), "0");
		assertEquals(pathNodes.get(5).getPoint().getId(), "5");
		assertEquals(pathNodes.get(6).getPoint().getId(), "10");
		assertEquals(pathNodes.get(7).getPoint().getId(), "11");
	}
	@Ignore
	@Test
	public void testAStar4to12_2() {
		RealPoint start, goal;
		start = testMap2.getPoint("4");
		goal = testMap2.getPoint("12");

		Path path = AStar.multi_AStar(start, goal).get(0);
		ArrayList<Node> pathNodes = path.getPath();

		assertEquals(path.getPath().size(), 7);
		assertEquals(pathNodes.get(0).getPoint().getId(), "4");
		assertEquals(pathNodes.get(1).getPoint().getId(), "9");
		assertEquals(pathNodes.get(2).getPoint().getId(), "14");
		assertEquals(pathNodes.get(3).getPoint().getId(), "19");
		assertEquals(pathNodes.get(4).getPoint().getId(), "18");
		assertEquals(pathNodes.get(5).getPoint().getId(), "17");
		assertEquals(pathNodes.get(6).getPoint().getId(), "12");
	}
	@Ignore
	@Test
	public void testConnected() {
		AllMaps.getInstance().addMap(testMap5);
		RealPoint start, goal;
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

	}

}