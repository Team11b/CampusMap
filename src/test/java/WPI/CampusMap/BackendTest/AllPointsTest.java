package WPI.CampusMap.BackendTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Point.AllPoints;

public class AllPointsTest {
	private static AllPoints points;

	
	
	@Before
	public void initialize() {
		points = AllPoints.getInstance();
		points.addPoint("1/One");
		points.addPoint("2/two");
		points.addPoint("3/three");
		points.addPoint("4/four");
		points.addPoint("5/five");
	}

	@Test
	public void testIfAddPointsDetectsMultiplePoints() {
		int count1 = points.getAllPointsShortName().size();

		assertFalse(points.addPoint("3/three"));

		int count2 = points.getAllPointsShortName().size();

		assertTrue(count1 == count2);
	}

	@Test
	public void testIfAddingMorePointsDetectsMultiplePointsWithArray() {
		int count1 = points.getAllPointsShortName().size();
		String[] strings = { "2/two", "4/four", "5/five" };

		assertTrue(3 == points.addAllPoints(strings).size());

		int count2 = points.getAllPointsShortName().size();

		assertTrue(count1 == count2);
	}

	@Test
	public void testIfAddingMorePointsDetectsMultiplePointsWithArrayList() {
		int count1 = points.getAllPointsShortName().size();
		ArrayList<String> list = new ArrayList<String>();
		list.add("2/two");
		list.add("4/four");
		list.add("5/five");

		assertTrue(3 == points.addAllPoints(list).size());

		int count2 = points.getAllPointsShortName().size();

		assertTrue(count1 == count2);
	}

	@Test
	public void testIfExistingPointsAreFound() {
		
	}
}
