package WPI.CampusMap.Backend.Core.Map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class RealMap implements IMap, java.io.Serializable {

	private static final long serialVersionUID = 3434772073791894710L;
	private static final String pngLocation = "maps/";
	private float scale;
	private String name;
	private HashMap<String, RealPoint> allPoints;
	private transient ImageIcon loadedImage;

	/**
	 * Creates a map with the given name and default values
	 * 
	 * @param name
	 *            The name of the map to be created.
	 */
	public RealMap(String name) {
		this.scale = 100;
		this.name = name;
		this.allPoints = new HashMap<String, RealPoint>();
	}

	public void validatePoints() {
		for (String key : allPoints.keySet()) {
			RealPoint point = allPoints.get(key);
			if (point != null) {
				point.constructNeighbors();
			} else {
				allPoints.remove(key);
			}
		}
	}

	/**
	 * Get the scale from inches to feet.
	 * 
	 * @return The scale from inches to feet.
	 */
	@Override
	public float getScale() {
		return this.scale;
	}

	/**
	 * Set the scale from inches to feet.
	 * 
	 * @param scale
	 *            the inches to feet scale.
	 */
	@Override
	public void setScale(float scale) {
		float oldScale = this.scale;
		this.scale = scale;

		float ratio = (float) scale / (float) oldScale;

		if (this.allPoints != null) {
			String[] keys = this.allPoints.keySet().toArray(new String[this.allPoints.size()]);
			for (String p : keys) {
				Coord oldCoord = this.allPoints.get(p).getCoord();
				oldCoord.setX(oldCoord.getX() / ratio);
				oldCoord.setY(oldCoord.getY() / ratio);
			}
		}

	}

	/**
	 * Gets the name of this map.
	 * 
	 * @return The name of this map.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the loaded image of the map png to display.
	 * 
	 * @return The loaded image to display for this map.
	 */
	@Override
	public ImageIcon getLoadedImage() {
		if (loadedImage == null) {
			loadImage();
		}
		return loadedImage;
	}

	/**
	 * loads the image of the map
	 */
	private void loadImage() {
		try {
			BufferedImage buffer = ImageIO.read(new File(pngLocation + this.getName() + ".png"));
			loadedImage = new ImageIcon(buffer.getScaledInstance(1000, 660, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Gets a point from the map.
	 * 
	 * @param id
	 *            The id of the point to get.
	 * @return The point with the id.
	 */
	@Override
	public RealPoint getPoint(String id) {
		return this.allPoints.get(id);
	}

	/**
	 * Removes the point with the given ID from the map array, and from the
	 * neighbor arrays of all points on the map
	 * 
	 * @param id
	 *            The ID of the point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	@Override
	public boolean removePoint(String id) {
		IPoint point = allPoints.get(id);
		return removePoint(point);
	}

	/**
	 * Removes the given point from the map array, and from the neighbor arrays
	 * of all points on the map
	 * 
	 * @param point
	 *            The point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	@Override
	public boolean removePoint(IPoint point) {
		// System.out.println("Remove: " + point.getId());
		ArrayList<IPoint> neighbors = point.getNeighborsP();
		for (IPoint pointN : neighbors) {
			if (!pointN.removeNeighbor(point))
				return false;
		}
		point.removeAllNeighbors();
		allPoints.remove(point.getId());

		return true;
	}

	/**
	 * Adds a point to the map. Does NOT connect the point to any other points.
	 * 
	 * @param point
	 *            a new Point to add
	 * @return true if the point was added, false if there already exists a
	 *         point with the same ID
	 */
	@Override
	public boolean addPoint(RealPoint point) {
		if (this.allPoints.containsKey(point.getId()))
			return false;

		this.allPoints.put(point.getId(), point);
		return true;
	}

	// TODO maybe move this to a static method?
	/**
	 * Adds an edge between two Points
	 * 
	 * @param point
	 *            the first Point
	 * @param other
	 *            the second Point
	 * @return true if the edge was added, false if one Points doesn't exist or
	 *         if the edge already exists
	 */
	// TODO maybe move this to a static method?
	@Override
	public boolean addEdge(IPoint point, IPoint other) {
		if (point.equals(other)) {
			return false;
		}
		boolean adder = point.addNeighbor(other);
		if (!(adder)) {
			return false;
		}
		other.addNeighbor(point);
		return true;
	}

	/**
	 * Removes an edge between two Points
	 * 
	 * @param point
	 *            the first Point
	 * @param other
	 *            the second Point
	 * @return true if the edge was removed, false if one Points doesn't exist
	 *         or if the edge does not exist
	 */
	@Override
	public boolean removeEdge(IPoint point, IPoint other) {
		if (this.allPoints.containsKey(point.getId()) && (this.allPoints.containsKey(other.getId()))) {
			boolean remover = point.removeNeighbor(other);
			if (!(remover)) {
				return false;
			}
			other.removeNeighbor(point);
			return true;
		}
		return false;
	}

	/**
	 * Removes the given point and adds it back under the newName
	 */
	@Override
	public void renamePoint(RealPoint p, String newName) {
		allPoints.remove(p.getId());
		allPoints.put(newName, p);
		System.out.println("Renamed point to" + this.getPoint(newName));
	}

	/**
	 * Uses the serializer to save the map data.
	 */
	@Override
	public void save() {
		for (RealPoint point : allPoints.values()) {
			point.validateNeighbors();
		}
		Serializer.save(this);
	}

	/**
	 * Returns the hashCode of the map
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public Collection<RealPoint> getAllPoints() {
		return allPoints.values();
	}

	@Override
	public boolean connectedToCampus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBuilding() {
		return getName().split("-")[0];
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof IMap) {
			IMap that = (IMap) other;
			boolean result = getName().equals(that.getName());
			return result;
		}
		return false;
	}

	@Override
	public ArrayList<IPoint> pointsConnectedToOtherMaps() {
		ArrayList<IPoint> points = new ArrayList<IPoint>();
		for (IPoint point : getAllPoints()) {
			if (!point.getNeighborPointsOnOtherMaps().isEmpty()) {
				points.add(point);
			}
		}

		return points;
	}

}
