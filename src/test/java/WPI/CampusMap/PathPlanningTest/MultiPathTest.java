package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;

public class MultiPathTest {
	public static Map testMap= new  Map();
	public static ConnectionPoint alpha;
	public static Point beta;
	public static Point gamma;
	public static Point delta;
	public static ConnectionPoint epsilon;
	public static ConnectionPoint zeta;
	public static Point eta;
	public static Point theta;
	public static ConnectionPoint iota;
	public static ConnectionPoint kappa;
	public static Point lambda;
	public static ConnectionPoint mu;

	public static Node nu;
	public static Node xi;
	public static Node omicron;
	public static Node pi;
	public static Node rho;
	public static Node sigma;
	public static Node tau;
	public static Node upsilon;
	public static Node phi;
	public static Node chi;
	public static Node psi;
	public static Node omega;

	public static Path alef;
	public static Path bet;
	public static Path gimel;
	public static Path dalet;
	public static Path bee;
	public static Path cee;
	public static Path dee;
	public static Path eee;
	public static Path hhh;

	public static MultiPath he;
	public static MultiPath aye;
	public static MultiPath eff;
	public static MultiPath gee;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testMap.setName("TestMap");
		alpha = new ConnectionPoint(null, null, "alpha","TestMap", null, "null");
		beta = new Point(null, null, "beta", "TestMap");
		gamma = new Point(null, null, "gamma", "TestMap");
		delta = new Point(null, null, "delta", "TestMap");
		epsilon = new ConnectionPoint(null, null, "epsilon","TestMap", null, "null");
		zeta = new ConnectionPoint(null, null, "zeta","TestMap", null, "null");
		eta = new Point(null, null, "eta", "TestMap");
		theta = new Point(null, null, "theta", "TestMap");
		iota = new ConnectionPoint(null, null, "iota","TestMap", null, "null");
		kappa = new ConnectionPoint(null, null, "kappa","TestMap", null, "null");
		lambda = new Point(null, null, "lambda", "TestMap");
		mu = new ConnectionPoint(null, null, "nu","TestMap", null, "null");

		nu = new Node(alpha, null);
		xi = new Node(beta, null);
		omicron = new Node(gamma, null);
		pi = new Node(delta, null);
		rho = new Node(epsilon, null);
		sigma = new Node(zeta, null);
		tau = new Node(eta, null);
		upsilon = new Node(theta, null);
		phi = new Node(iota, null);
		chi = new Node(kappa, null);
		psi = new Node(lambda, null);
		omega = new Node(mu, null);

		alef = new Path();
		bet = new Path();
		gimel = new Path();
		dalet = new Path();
		bee = new Path();
		cee = new Path();
		dee = new Path();
		eee = new Path();
		hhh = new Path();

		alef.addNode(nu);
		alef.addNode(xi);
		alef.addNode(omicron);
		alef.addNode(pi);
		alef.addNode(rho);
		alef.addNode(sigma);
		alef.addNode(tau);
		alef.addNode(upsilon);
		alef.addNode(phi);
		alef.addNode(chi);
		alef.addNode(psi);
		alef.addNode(omega);

		bet.addNode(nu);
		bet.addNode(xi);
		bet.addNode(omicron);
		bet.addNode(pi);
		bet.addNode(rho);

		gimel.addNode(sigma);
		gimel.addNode(tau);
		gimel.addNode(upsilon);
		gimel.addNode(phi);

		dalet.addNode(chi);
		dalet.addNode(psi);
		dalet.addNode(omega);
		
		bee.addNode(xi);
		bee.addNode(omicron);
		bee.addNode(pi);
		bee.addNode(rho);
		bee.addNode(sigma);
		bee.addNode(tau);
		bee.addNode(upsilon);
		bee.addNode(phi);
		bee.addNode(chi);
		bee.addNode(psi);
		bee.addNode(omega);
		
		eee.addNode(xi);
		eee.addNode(omicron);
		eee.addNode(pi);
		eee.addNode(rho);
		
		cee.addNode(nu);
		cee.addNode(xi);
		cee.addNode(omicron);
		cee.addNode(pi);
		cee.addNode(rho);
		cee.addNode(sigma);
		cee.addNode(tau);
		cee.addNode(upsilon);
		cee.addNode(phi);
		cee.addNode(chi);
		cee.addNode(psi);
		
		hhh.addNode(chi);
		hhh.addNode(psi);
		
		dee.addNode(xi);
		dee.addNode(omicron);
		dee.addNode(pi);
		dee.addNode(rho);
		dee.addNode(sigma);
		dee.addNode(tau);
		dee.addNode(upsilon);
		dee.addNode(phi);
		dee.addNode(chi);
		dee.addNode(psi);

		he = new MultiPath(alef);
		aye = new MultiPath(bee);
		eff = new MultiPath(cee);
		gee = new MultiPath(dee);
	}

	@Test
	public void testParse1() {
		assertEquals(he.get(0), bet);
		assertEquals(he.get(1), gimel);
		assertEquals(he.get(2), dalet);
	}

	@Test
	public void testParse2() {
		assertEquals(he.size(), 3);
	}
	
	@Test
	public void testParse3() {
		assertEquals(aye.get(0), eee);
		assertEquals(aye.get(1), gimel);
		assertEquals(aye.get(2), dalet);
	}
	
	@Test
	public void testParse4() {
		assertEquals(eff.get(0), bet);
		assertEquals(eff.get(1), gimel);
		assertEquals(eff.get(2), hhh);
	}
	
	@Test
	public void testParse5() {
		assertEquals(gee.get(0), eee);
		assertEquals(gee.get(1), gimel);
		assertEquals(gee.get(2), hhh);
	}

}
