package WPI.CampusMap.Backend.PathPlanning;

import java.util.Comparator;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.IPoint;

public class AStarPathProcessor extends PathProcessor {

	NodeProcessor nProcessor;

	public AStarPathProcessor() {
		super();
	}

	public AStarPathProcessor(NodeProcessor nProcessor) {
		super();
		this.nProcessor = nProcessor;
	}

	// on map with goal #1
	// on building with goal #2
	// on campus map #3
	// anything else
	private static class NodeComparator implements Comparator<Node> {
		public int compare(Node n1, Node n2) {
			IPoint p1 = n1.getPoint();
			IPoint p2 = n2.getPoint();
			IPoint pGoal = goal.getPoint();

			// on map with goal
			if ((p1.getMap().equals(pGoal.getMap()) && (!(p2.getMap().equals(pGoal.getMap()))))) {
				return -1;
			} else if ((!(p1.getMap().equals(pGoal.getMap())) && (!(p2.getMap().equals(pGoal.getMap()))))) {
				return 1;
			} else if ((p1.getMap().equals(pGoal.getMap()) && (p2.getMap().equals(pGoal.getMap())))) {
				return Float.compare(n1.getTotalCost(), n2.getTotalCost());
			}
			
			// on building with goal
			if ((p1.getBuilding().equals(pGoal.getBuilding())) && (!(p2.getBuilding().equals(pGoal.getBuilding())))) {
				return -1;
			}
			else if ((!(p1.getBuilding().equals(pGoal.getBuilding()))) && (p2.getBuilding().equals(pGoal.getBuilding()))) {
				return 1;
			}
			
			// on campus map
//			if ((p1.getMap().equals(AllMaps.getInstance().CampusMap)) && (!(p2.getMap().equals(AllMaps.getInstance().CampusMap)))) {
//				return -1;
//			}
//			else if (!(p1.getMap().equals(AllMaps.getInstance().CampusMap)) && (p2.getMap().equals(AllMaps.getInstance().CampusMap))) {
//				return 1;
//			}
			
			// connects to campus
//			if ((p1.connectToCampus()) && (!(p2.connectToCampus()))) {
//				return -1;
//			}
//			else if ((!(p1.connectToCampus())) && (p2.connectToCampus())) {
//				return 1;
//			}

			return Float.compare(n1.getTotalCost(), n2.getTotalCost());

		}
	}

	@Override
	protected Comparator<Node> getNodeCompartor() {
		return new NodeComparator();
	}

	@Override
	protected NodeProcessor getProcessorChain() {
		return nProcessor;
	}

}
