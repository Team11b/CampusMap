package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.Core.Point.RealPoint;

public class BetweenMapsProcessor extends NodeProcessor {
	private static final int NO_COST = 0;
	private static final int DOOR_COST = NO_COST;
	private static final int ELV_COST = 20;
	private static final int STAIR_COST = 50;

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
			node.modifyHeuristicCost(BetweenMapsProcessor.NO_COST);
		}
		else if (node.getPoint().getType().equals(RealPoint.OUT_DOOR)) {
			node.modifyHeuristicCost(BetweenMapsProcessor.DOOR_COST);
		}
		else if (node.getPoint().getType().equals(RealPoint.ELEVATOR)) {
			node.modifyHeuristicCost(BetweenMapsProcessor.ELV_COST);
		}
		else {
			node.modifyHeuristicCost(BetweenMapsProcessor.STAIR_COST);
		}

	}

}
