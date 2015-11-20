package UI;

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
				AppUIObject.currentMap.createPointOnMap(e);
			} else if (uiObject.edgeMode) {
				System.out.println("You added edge X: " + e.getX() + " Y: " + e.getY());
				MapPanel.addEdgeOnMap(e);
			} else if (uiObject.deleteMode) {
				System.out.println("You deleted X: " + e.getX() + " Y: " + e.getY());
				uiObject.mapPanel.deletePointOnMap(e);
			} else {
				// if(selectPointOnMap(e))
				MapPanel.removeEdgeOnMap(e);
			}
		} else {
			if (AppUIObject.startPoint == null) {
				if (MapPanel.selectPointOnMap(e)) {
					AppUIObject.startPoint = AppUIObject.selectedPoint;
				}
			} else if (AppUIObject.endPoint == null) {
				if (MapPanel.selectPointOnMap(e)) {
					AppUIObject.endPoint = AppUIObject.selectedPoint;
					uiObject.btnGetDirections.setEnabled(true);
				}
			} else {
				AppUIObject.startPoint = null;
				AppUIObject.endPoint = null;

				// clean up route
				AppUIObject.currentRoute = null;
				uiObject.btnGetDirections.setEnabled(false);

				if (MapPanel.selectPointOnMap(e)) {

					AppUIObject.startPoint = AppUIObject.selectedPoint;
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

