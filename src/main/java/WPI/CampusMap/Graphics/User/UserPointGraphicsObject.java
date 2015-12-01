package WPI.CampusMap.Graphics.User;

import java.awt.Color;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Core.TypedRef;
import WPI.CampusMap.Graphics.PointGraphicsObject;
import WPI.CampusMap.Graphics.RealMouseEvent;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.AStar.AStar;
import WPI.CampusMap.UI.AppUIObject;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	private static LinkedList<TypedRef<UserPointGraphicsObject>> selectedRoute = new LinkedList<>();
	private static MultiPath lastRoutedPath;
	
	public static LinkedList<TypedRef<UserPointGraphicsObject>> getSelectedRoute()
	{
		return selectedRoute;
	}
	
	public static void pushPointOnRoute(UserPointGraphicsObject point)
	{
		if(!point.selectedToRoute)
		{
			point.selectedToRoute = true;
			TypedRef<UserPointGraphicsObject> ref = new TypedRef<UserPointGraphicsObject>(point);
			selectedRoute.add(ref);
			
			AppUIObject.getInstance().onPointAddedToRoute(point.getRepresentedObject());
		}
	}
	
	public static void route()
	{
		UserPathGraphicsObject.deleteAll();
		
		lastRoutedPath = new MultiPath();
		
		for(int i = 1; i < selectedRoute.size(); i++)
		{
			TypedRef<UserPointGraphicsObject> current = selectedRoute.get(i);
			TypedRef<UserPointGraphicsObject> last = selectedRoute.get(i - 1);
			
			MultiPath subRoute = AStar.multi_AStar(last.getValue().getRepresentedObject(), current.getValue().getRepresentedObject());
			
			lastRoutedPath = MultiPath.join(lastRoutedPath, subRoute);
		}
		
		for(String map : lastRoutedPath.getReferencedMaps())
		{
			UserGraphicalMap graphicalMap = UserGraphicalMap.loadGraphicalMap(Map.getMap(map));
			graphicalMap.setPathSections(lastRoutedPath.getMapPath(map));
		}
	}
	
	public static void clearRoute()
	{
		for(TypedRef<UserPointGraphicsObject> ref : selectedRoute)
		{
			ref.getValue().selectedToRoute = false;
			ref.release();
		}
		
		UserPathGraphicsObject.deleteAll();
		
		selectedRoute.clear();
		
		AppUIObject.getInstance().onRouteCleared();
	}
	
	private boolean selectedToRoute;
	
	public UserPointGraphicsObject(Point backend, UserGraphicalMap owner) 
	{
		super(backend, owner);		
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor() 
	{
		if(selectedToRoute)
		{
			return Color.yellow;
		}
		
		return super.getColor();
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e)
	{
		pushPointOnRoute(this);
	}
}
