package WPI.CampusMap.PathPlanningTest;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;

public class MultiPathTest {
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
	
	public static MultiPath he;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		alpha = new ConnectionPoint(null, null, "alpha", null, null);
		beta = new Point(null, null, "beta");
		gamma = new Point(null, null, "gamma");
		delta = new Point(null, null, "delta");
		epsilon = new ConnectionPoint(null, null, "epsilon", null, null);
		zeta = new ConnectionPoint(null, null, "zeta", null, null);
		eta = new Point(null, null, "eta");
		theta = new Point(null, null, "theta");
		iota = new ConnectionPoint(null, null, "iota", null, null);
		kappa = new ConnectionPoint(null, null, "kappa", null, null);
		lambda = new Point(null, null, "lambda");
		mu = new ConnectionPoint(null, null, "nu", null, null);

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
		
		he = new MultiPath(alef);
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

}
