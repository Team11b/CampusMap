package WPI.CampusMap.Backend.PathPlanning;

import java.util.Comparator;

import WPI.CampusMap.Backend.Core.Point.IPoint;

public class AStar extends PathProcessor {

	NodeProcessor nProcessor;
	
	public AStar(){
		super();
	}
	
	public AStar(NodeProcessor nProcessor){
		super();
		this.nProcessor = nProcessor;
	}
	
	private static class NodeComparator implements Comparator<Node>
	 {
	     public int compare(Node n1, Node n2)
	     {
	    	 IPoint p1 = n1.point;
	    	 IPoint p2 = n2.point;
	    	 IPoint pGoal = goal.point;
	    	 if(p1.getMap().equals(p2.getMap())){
	    		 return Float.compare(n1.getTotalCost(), n2.getTotalCost()); 
	    	 } else if(p1.getMap().equals(pGoal.getMap())){
	    		 return -1;
	    	 } else if(p2.getMap().equals(pGoal.getMap())){
	    		 return 1;
	    	 }
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
