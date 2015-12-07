package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MapPanel extends JPanel
{
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
	}

}
