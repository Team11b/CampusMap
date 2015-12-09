package WPI.CampusMap.Backend.PathPlanning;

public class DistanceProcessor extends NodeProcessor {
	
	public DistanceProcessor() {}

	public DistanceProcessor(NodeProcessor child) {
		super(child);
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		if(node.getPoint().getMap().equals(goal.getPoint().getMap())){
			node.modifyHeuristicCost(node.getDistance(goal.getPoint()));
		}
	}

}
