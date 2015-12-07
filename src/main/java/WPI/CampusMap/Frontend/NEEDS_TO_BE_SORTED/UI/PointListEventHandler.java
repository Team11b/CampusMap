package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

public interface PointListEventHandler
{
	void pointDescriptorAdded(PointListElement element);
	
	void pointDescriptorRemoved(PointListElement element);
	
	void pointDescriptorRenamed(PointListElement element, String oldName);
	
	void pointDescriptorShow(PointListElement element);
	
	void pointDescriptorMoved(PointListElement element);
}
