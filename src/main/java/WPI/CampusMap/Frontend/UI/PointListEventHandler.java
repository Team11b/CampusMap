package WPI.CampusMap.Frontend.UI;

public interface PointListEventHandler
{
	void pointDescriptorAdded(PointListElement element);
	
	void pointDescriptorRemoved(PointListElement element);
	
	/**
	 * Called to check to see if a new name for a point descriptor is a valid name.
	 * @param element The element being renamed.
	 * @param newName The new name for the element.
	 * @return True if the name is valid, false otherwise.
	 */
	boolean pointDescriptorNameCheck(PointListElement element, String newName);
	
	/**
	 * Called when a point descriptor is renamed. This is only called if the name check passes, the element will contain old name information.
	 * @param element The element being renamed.
	 * @param oldName The old name of the element.
	 */
	void pointDescriptorRenamed(PointListElement element, String oldName);
	
	/**
	 * Called if a point descriptor fails to rename to a new name.
	 * @param element The element that failed to be renamed.
	 * @param failedName The name that caused the fail.
	 */
	void pointDescriptorNameCheckFailed(PointListElement element, String failedName);
	
	void pointDescriptorShow(PointListElement element);
	
	void pointDescriptorMoved(PointListElement element);
}
