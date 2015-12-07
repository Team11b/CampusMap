package WPI.CampusMap.Backend.PathPlanning;

public class DistanceProcessor extends NodeProcessor {
	
	public DistanceProcessor() {}

	public DistanceProcessor(NodeProcessor child) {
		this.child = child;
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		node.setHeuristicCost(node.getDistance(goal.getPoint()));
	}

}
