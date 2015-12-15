package WPI.CampusMap.Frontend.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class MapPanel extends JPanel
{
	public MapPanel()
	{
		MouseEvents events = new MouseEvents();
		addMouseListener(events);
		addMouseMotionListener(events);
		addMouseWheelListener(events);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4728664739620550736L;
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D newGraphics = (Graphics2D) g.create();
		
		AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(this);
		
		UIMode mode = window.getUIMode();
		if(mode != null)
			window.getUIMode().onDraw(newGraphics);
		
		newGraphics.dispose();
	}
	
	
	private class MouseEvents implements MouseInputListener, MouseWheelListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(e.getComponent());
			
			UIMode mode = window.getUIMode();
			if(mode != null)
				window.getUIMode().onMouseClickMap(e);
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(e.getComponent());
			
			UIMode mode = window.getUIMode();
			if(mode != null)
				window.getUIMode().onMouseEnterMap(e);
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(e.getComponent());
			
			UIMode mode = window.getUIMode();
			if(mode != null)
				window.getUIMode().onMouseLeaveMap(e);
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(e.getComponent());
			
			UIMode mode = window.getUIMode();
			if(mode != null)
				window.getUIMode().onMouseDraggedOverMap(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(e.getComponent());
			
			UIMode mode = window.getUIMode();
			if(mode != null)
				window.getUIMode().onMouseMoveOverMap(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			AppMainWindow window = (AppMainWindow) SwingUtilities.getWindowAncestor(e.getComponent());
			
			UIMode mode = window.getUIMode();
			if(mode != null)
				window.getUIMode().onMouseScrollOnMap(e.getUnitsToScroll());
		}
		
	}
}
