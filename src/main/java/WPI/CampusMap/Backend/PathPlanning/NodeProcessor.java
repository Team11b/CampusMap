package WPI.CampusMap.Backend.PathPlanning;

import org.jboss.resteasy.spi.NotImplementedYetException;

/**
 * Base class for a piece of code that processes a node during path finding.
 * @author Benny
 *
 */
public abstract class NodeProcessor
{
	/**
	 * Creates a node processor.
	 */
	public NodeProcessor()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Creates a node processor to process after its child.
	 * @param child The child node processor.
	 */
	public NodeProcessor(NodeProcessor child)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Gets the child node processor of this processor.
	 * @return The child node processor of this processor.
	 */
	public NodeProcessor getChild()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Executes processing this node processor and all linked children.
	 */
	protected void execute()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	protected abstract void onProcessNode();
}
