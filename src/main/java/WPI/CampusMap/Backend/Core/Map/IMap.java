package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

public interface IMap {
	public float getScale();
	public String getBuilding();
	public void setScale(float f);
	public String getName();
	public String getDisplayName();
	public ImageIcon getLoadedImage();
	public RealPoint getPoint(String id);
	public Collection<RealPoint> getAllPoints();
	public boolean removePoint(String id);
	public boolean removePoint(IPoint point);
	public boolean addPoint(RealPoint point);
	public IPoint createPoint(Coord location);
	public boolean addEdge(IPoint point, IPoint other);
	public boolean removeEdge(IPoint point, IPoint other);
	public void renamePoint(RealPoint p, String newName);
	public boolean connectedToCampus();
	public ArrayList<IPoint> pointsConnectedToOtherMaps();
	public void save();
	public boolean unsavedChanged();
	public void changed();
	String getFloorName();
}