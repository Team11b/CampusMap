package WPI.CampusMap.Backend.PathPlanning;

import java.util.Comparator;

public class AStar extends PathProcessor {

	NodeProcessor nProcessor;
	
	public AStar(NodeProcessor nProcessor){
		this.nProcessor = nProcessor;
	}
	
	private static class NodeComparator implements Comparator<Node>
	 {
	     public int compare(Node c1, Node c2)
	     {
	    	 return 0;
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
