package WPI.CampusMap.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapMouseListener implements MouseListener {
	private AppUIObject uiObject;
	MapMouseListener(AppUIObject uiObject){
		this.uiObject = uiObject;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (uiObject.devMode) {
			if (uiObject.placeMode) {
				System.out.println("Placing point on Map X: " + e.getX() + " Y: " + e.getY());
				uiObject.mapPanel.currentMap.createPointOnMap(e);
			} else if (uiObject.edgeMode) {
				System.out.println("You added edge X: " + e.getX() + " Y: " + e.getY());
				uiObject.mapPanel.addEdgeOnMap(e);
			} else if (uiObject.deleteMode) {
				System.out.println("You deleted X: " + e.getX() + " Y: " + e.getY());
				uiObject.mapPanel.deletePointOnMap(e);
			} else {
				// if(selectPointOnMap(e))
				uiObject.mapPanel.removeEdgeOnMap(e);
			}
		} else {
			if (uiObject.mapPanel.startPoint == null) {
				if (uiObject.mapPanel.selectPointOnMap(e)) {
					uiObject.mapPanel.startPoint = uiObject.mapPanel.selectedPoint;
				}
			} else if (uiObject.mapPanel.endPoint == null) {
				if (uiObject.mapPanel.selectPointOnMap(e)) {
					uiObject.mapPanel.endPoint = uiObject.mapPanel.selectedPoint;
					uiObject.btnGetDirections.setEnabled(true);
				}
			} else {
				uiObject.mapPanel.startPoint = null;
				uiObject.mapPanel.endPoint = null;

				// clean up route
				uiObject.mapPanel.currentRoute = null;
				uiObject.btnGetDirections.setEnabled(false);

				if (uiObject.mapPanel.selectPointOnMap(e)) {

					uiObject.mapPanel.startPoint = uiObject.mapPanel.selectedPoint;
				}
			}
			System.out.println("You clicked X: " + e.getX() + " Y: " + e.getY());
		}
		uiObject.reDrawUI();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

