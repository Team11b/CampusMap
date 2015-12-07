package WPI.CampusMap.Backend.PathPlanning;


/**
 * Base class for a piece of code that processes a node during path finding.
 * @author Benny
 *
 */
public abstract class NodeProcessor
{
	private NodeProcessor child;
	/**
	 * Creates a node processor.
	 */
	public NodeProcessor(){}
	
	/**
	 * Creates a node processor to process after its child.
	 * @param child The child node processor.
	 */
	public NodeProcessor(NodeProcessor child)
	{
		this.child = child;
	}
	
	/**
	 * Gets the child node processor of this processor.
	 * @return The child node processor of this processor.
	 */
	public NodeProcessor getChild()
	{
		return child;
	}
	
	/**
	 * Executes this node processor and all linked children on the given node.
	 * @param node to process
	 */
	protected void execute(Node node)
	{
		onProcessNode(node);
		if(child != null){
			child.execute(node);
		}
	}

	/**
	 * Processes the given node.
	 * @param node to process
	 */
	protected abstract void onProcessNode(Node node);
}
