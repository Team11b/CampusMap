package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.Path;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;

public class MultiPathTest {
	public static Point alpha;
	public static Point beta;
	public static Point gamma;
	public static Point delta;
	public static Point epsilon;
	public static Point zeta;
	public static Point eta;
	public static Point theta;
	public static Point iota;
	public static Point kappa;
	public static Point lambda;
	public static Point mu;

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
		alpha = new Point(null, null, "alpha", null);
		beta = new Point(null, null, "beta", null);
		gamma = new Point(null, null, "gamma", null);
		delta = new Point(null, null, "delta", null);
		epsilon = new Point(null, null, "epsilon", null);
		zeta = new Point(null, null, "zeta", null);
		eta = new Point(null, null, "eta", null);
		theta = new Point(null, null, "theta", null);
		iota = new Point(null, null, "iota", null);
		kappa = new Point(null, null, "kappa", null);
		lambda = new Point(null, null, "lambda", null);
		mu = new Point(null, null, "nu", null);

		nu = new Node(alpha, null, null);
		xi = new Node(beta, null, null);
		omicron = new Node(gamma, null, null);
		pi = new Node(delta, null, null);
		rho = new Node(epsilon, null, null);
		sigma = new Node(zeta, null, null);
		tau = new Node(eta, null, null);
		upsilon = new Node(theta, null, null);
		phi = new Node(iota, null, null);
		chi = new Node(kappa, null, null);
		psi = new Node(lambda, null, null);
		omega = new Node(mu, null, null);

		alef = new Path(1);
		bet = new Path(1);
		gimel = new Path(1);
		dalet = new Path(1);
		bee = new Path(1);
		cee = new Path(1);
		dee = new Path(1);
		eee = new Path(1);
		hhh = new Path(1);

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

	@Ignore
	@Test
	public void testParse1() {
		assertEquals(he.get(0), bet);
		assertEquals(he.get(1), gimel);
		assertEquals(he.get(2), dalet);
	}

	@Ignore
	@Test
	public void testParse2() {
		assertEquals(he.size(), 3);
	}

	@Ignore
	@Test
	public void testParse3() {
		assertEquals(aye.get(0), eee);
		assertEquals(aye.get(1), gimel);
		assertEquals(aye.get(2), dalet);
	}

	@Ignore
	@Test
	public void testParse4() {
		assertEquals(eff.get(0), bet);
		assertEquals(eff.get(1), gimel);
		assertEquals(eff.get(2), hhh);
	}

	@Ignore
	@Test
	public void testParse5() {
		assertEquals(gee.get(0), eee);
		assertEquals(gee.get(1), gimel);
		assertEquals(gee.get(2), hhh);
	}

}