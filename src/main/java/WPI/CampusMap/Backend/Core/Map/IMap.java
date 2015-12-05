package WPI.CampusMap.Backend.Core.Map;

import java.io.Serializable;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.IPoint;

public interface IMap {
	public float getScale();
	public void setScale(float f);
	public String getName();
	public ImageIcon getLoadedImage();
	public IPoint getPoint(String id);
	public boolean removePoint(String id);
	public boolean removePoint(IPoint point);
	public boolean addPoint(IPoint point);
	public boolean addEdge(IPoint point, IPoint other);
	public boolean removeEdge(IPoint point, IPoint other);
	public void renamePoint(IPoint p, String newName);
	public void save();
}