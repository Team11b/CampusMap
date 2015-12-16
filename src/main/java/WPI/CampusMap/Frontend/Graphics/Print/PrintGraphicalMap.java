package WPI.CampusMap.Frontend.Graphics.Print;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Frontend.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.Graphics.Print.PrintPointGraphicsObject.PrintFlags;
import WPI.CampusMap.Frontend.Graphics.User.UserPointGraphicsObject;
import WPI.CampusMap.Frontend.UI.UserMode;

public class PrintGraphicalMap extends GraphicalMap
{
	private Path.Section section;
	
	public PrintGraphicalMap(String map, Path.Section section)
	{
		super(map, null);
		this.section = section;
	}
	
	@Override
	public void spawnMap()
	{
		int i = 0;
		for(IPoint p : section)
		{
			PrintFlags flags;
			if(i == 0)
				flags = PrintFlags.StartSection;
			else if(i == section.size() - 1)
				flags = PrintFlags.EndSection;
			else
				flags = PrintFlags.Node;
			
			new PrintPointGraphicsObject(p, this, flags);
			
			i++;
		}
		
		new PrintPathGraphicsObject(section, this);
	}
}
