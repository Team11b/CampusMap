package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

public interface PointListEventHandler
{
	void PointDescriptorAdded(PointListElement element);
	
	void PointDescriptorRemoved(PointListElement element);
	
	void PointDescriptorRenamed(PointListElement element);
	
	void PointDescriptorShow(PointListElement element);
	
	void PointDescriptorMoved(PointListElement element);
}
