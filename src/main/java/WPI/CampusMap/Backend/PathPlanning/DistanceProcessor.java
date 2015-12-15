package WPI.CampusMap.Backend.PathPlanning;

public class DistanceProcessor extends NodeProcessor {
	
	public DistanceProcessor() {}

	public DistanceProcessor(NodeProcessor child) {
		super(child);
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		
		Node last = node.getLast();
		if(node.getPoint().getMap().equals(last.getPoint().getMap())){
			float dist = node.getDistance(last.getPoint());
			if(dist < 0){
				dist = 0;
			}
			node.setAccumulatedDistance(last.getAccumulatedDistance() + dist);
		}
	}

}
