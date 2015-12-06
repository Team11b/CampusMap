package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.awt.Color;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.Core.Ref.TypedRef;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.Path;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.AppUIObject;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	private static LinkedList<TypedRef<UserPointGraphicsObject>> selectedRoute = new LinkedList<>();
	private static MultiPath lastRoutedPath;
	
	public static LinkedList<TypedRef<UserPointGraphicsObject>> getSelectedRoute()
	{
		return selectedRoute;
	}
	
	private boolean selectedToRoute;
	
	public static void pushPointOnRoute(UserPointGraphicsObject point)
	{
		//Clear out when adding a start point
		if(lastRoutedPath != null){
			AppUIObject.getInstance().destinations.resetLastPoint();
			clearSelected();
			lastRoutedPath = null;
		}
		
		if(!point.selectedToRoute)
		{
			if(selectedRoute.size() == 0)
			{
				UserPathGraphicsObject.deleteAll();
				AppUIObject.getInstance().onRouteCleared();
			}
			
			point.selectedToRoute = true;
			TypedRef<UserPointGraphicsObject> ref = new TypedRef<UserPointGraphicsObject>(point);
			selectedRoute.add(ref);			
			
			AppUIObject.getInstance().onPointAddedToRoute(point.getRepresentedObject());
		}
	}
	
	public static void pullPointOnRoute(int index){
		System.out.println("Index is "+ index);
		
		//get the graphical object and change color
		TypedRef<UserPointGraphicsObject> point = selectedRoute.get(index);
		point.getValue().selectedToRoute = false;
		
		//remove from Route
		point.release();
		selectedRoute.remove(index);
	}
	
	public static MultiPath route()
	{
		lastRoutedPath = null;
		
		for(int i = 1; i < selectedRoute.size(); i++)
//		{
//			TypedRef<UserPointGraphicsObject> current = selectedRoute.get(i);
//			TypedRef<UserPointGraphicsObject> last = selectedRoute.get(i - 1);
//			
//			MultiPath subRoute = AStar.multi_AStar(last.getValue().getRepresentedObject(), current.getValue().getRepresentedObject());
//			
//			if(lastRoutedPath != null)
//				lastRoutedPath = MultiPath.join(lastRoutedPath, subRoute);
//			else
//				lastRoutedPath = subRoute;
//		}
		
		for(String map : lastRoutedPath.getReferencedMaps())
		{
			UserGraphicalMap graphicalMap = UserGraphicalMap.loadGraphicalMap(AllMaps.getInstance().getMap(map));
//			Path path = lastRoutedPath.getMapPath(map);
			graphicalMap.setPathSections(new LinkedList<Path>());
		}
		
		//clearSelected();
		return lastRoutedPath;
	}
	
	public static void clearSelected()
	{
		for(TypedRef<UserPointGraphicsObject> ref : selectedRoute)
		{
			ref.getValue().selectedToRoute = false;
			ref.release();
		}
		
		selectedRoute.clear();
		
		AppUIObject.getInstance().onRouteCleared();
	}
	
	public UserPointGraphicsObject(RealPoint backend, UserGraphicalMap owner) 
	{
		super(backend, owner);		
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor() 
	{
		if(selectedToRoute)
		{
			if(this == getSelectedRoute().getFirst().getValue())
			{				
				return Color.green;
			}				
			else if(this == getSelectedRoute().getLast().getValue())
				return Color.blue;
			else
			return Color.yellow;
		}
		else if(lastRoutedPath != null)
		{
			/*LinkedList<Path> paths = lastRoutedPath.getMapPath(getOwner().getMap());
			for(Path p : paths)
			{
				if(this.equals(p.getPath().get(0).getPoint()))
					return Color.green;
				else
				{
					ArrayList<Node> nodes = p.getPath();
					if(this.equals(nodes.get(nodes.size() - 1).getPoint()))
						return Color.blue;
				}
			}*/
		}
		
		return super.getColor();
	}
	
	@Override
	public float getAlpha() {
		// TODO Auto-generated method stub
		if(selectedToRoute)
			return 1.0f;
		
		if(getOwner().getHoverObject() == this)
			return 0.5f;
		
		return 0.1f;
	}
	
	@Override
	public boolean isMouseOver(RealMouseEvent e) {
		Coord mouseCoord = new Coord(e.getX(), e.getY());
		Coord screenPosition = getOwner().getScreenCoord(getRepresentedObject().getCoord());
		return mouseCoord.distance(screenPosition) <= 30;
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e)
	{
		pushPointOnRoute(this);
	}
}
