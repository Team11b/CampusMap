package WPI.CampusMap.Backend.PathPlanning;

public class DistanceProcessor extends NodeProcessor {

	@Override
	protected void onProcessNode(Node node, Node goal) {
		node.setHeuristicCost(node.getDistance(goal.point));
	}

}
