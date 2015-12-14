package WPI.CampusMap.Frontend.Graphics.User;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;

public class UserPathGraphicsObject extends GraphicsObject<Section, UserGraphicalMap> {
	private float phase;
	private boolean backgroundLine = false;

	private boolean selected;

	public UserPathGraphicsObject(Section path, UserGraphicalMap owner) {
		super(path, owner);
	}

	@Override
	public void onDeleted() {
	}

	@Override
	public int getDrawBatch() {
		return -1;
	}

	@Override
	public void onDraw(Graphics2D graphics) {
		phase += 0.33f;

		LinkedList<IPoint> points = getRepresentedObject().getPoints();

		for (int i = 1; i < points.size(); i++) {
			IPoint currentPoint = points.get(i);
			IPoint lastPoint = points.get(i - 1);

			Coord currentRenderCoord = getOwner().getRenderFromWorld(currentPoint.getCoord());
			Coord lastRenderCoord = getOwner().getRenderFromWorld(lastPoint.getCoord());

			//draw background line
			backgroundLine = true;
			graphics.setColor(getColor());
			graphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f));
			Line2D.Float bLine = new Line2D.Float(currentRenderCoord.getX(), currentRenderCoord.getY(),
					lastRenderCoord.getX(), lastRenderCoord.getY());
			graphics.draw(bLine);
			backgroundLine = false;
			

			graphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f,
					new float[] { 10.0f }, phase));
			
			graphics.setColor(getColor());
			Line2D.Float line = new Line2D.Float(currentRenderCoord.getX(), currentRenderCoord.getY(),
					lastRenderCoord.getX(), lastRenderCoord.getY());

			graphics.draw(line);
		}

		graphics.setStroke(new BasicStroke(1));
	}

	@Override
	public float getAlpha() {
		return 1;
	}

	@Override
	public Color getColor() {
		Color color;
		if(backgroundLine) color =  new Color(255,255,0);
		else color = new Color(0, 128, 255);
		int avg = (int) Math.sqrt(Math.pow(color.getRed(),2)+(Math.pow(color.getGreen(),2)+(Math.pow(color.getBlue(),2))))/3;
		if(!selected) color = new Color(avg,avg,avg);
		return color;
	}

	@Override
	public void onMouseOver(RealMouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseLeave(RealMouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMove(RealMouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseClick(RealMouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseDrag(RealMouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isMouseOver(RealMouseEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Coord getWorldPosition() {
		return new Coord(0, 0);
	}

	public void setSelected(boolean isSelected) {
		selected = isSelected;
	}

	public boolean getIsSelected() {
		return selected;
	}
}
