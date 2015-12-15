package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.IPoint;

public class DistanceProcessor extends NodeProcessor {
	
	public DistanceProcessor() {}

	public DistanceProcessor(NodeProcessor child) {
		super(child);
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		
		Node last = node.getLast();
		if(node.getPoint().getMap().equals(last.getPoint().getMap())){
			node.setAccumulatedDistance(last.getAccumulatedDistance() + node.getDistance(last.getPoint()));
		}
		
		String campusMap = AllMaps.getInstance().CampusMap;
//		if(node.getPoint().getMap().equals(campusMap)){
//			IPoint buildingPoint = AllMaps.getInstance().getMap(campusMap).getPoint(goal.getPoint().getBuilding());
//			if(buildingPoint != null){
//				node.modifyHeuristicCost(node.getDistance(buildingPoint));
//			}
//		}
	}

}
