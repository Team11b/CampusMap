package WPI.CampusMap.Backend.PathPlanning;

public class BetweenMapsProcessor extends NodeProcessor {
	private static final int extraCost = 50;

	public BetweenMapsProcessor() {
		// TODO Auto-generated constructor stub
	}

	public BetweenMapsProcessor(NodeProcessor child) {
		super(child);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		if (node.getPoint().getMap().equals(node.getLast().getPoint().getMap())) {
			node.modifyHeuristicCost(0);
		}
		else {
			node.modifyHeuristicCost(BetweenMapsProcessor.extraCost);
		}

	}

}
