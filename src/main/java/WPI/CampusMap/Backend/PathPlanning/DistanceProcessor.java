package WPI.CampusMap.Backend.PathPlanning;

public class DistanceProcessor extends NodeProcessor {
	
	public DistanceProcessor() {}

	public DistanceProcessor(NodeProcessor child) {
		super(child);
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		node.modifyHeuristicCost(node.getDistance(goal.getPoint()));
	}

}
