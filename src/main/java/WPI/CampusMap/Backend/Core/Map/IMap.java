package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

public interface IMap {
	public float getScale();
	public void setScale(float f);
	public String getName();
	public ImageIcon getLoadedImage();
	public RealPoint getPoint(String id);
	public ArrayList<RealPoint> getAllPoints();
	public boolean removePoint(String id);
	public boolean removePoint(IPoint point);
	public boolean addPoint(RealPoint point);
	public boolean addEdge(IPoint point, IPoint other);
	public boolean removeEdge(IPoint point, IPoint other);
	public void renamePoint(RealPoint p, String newName);
	public void save();
}