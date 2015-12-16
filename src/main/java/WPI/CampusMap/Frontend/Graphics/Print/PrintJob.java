package WPI.CampusMap.Frontend.Graphics.Print;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Backend.PathPlanning.Route.Instruction;
import WPI.CampusMap.Backend.PathPlanning.Route.Route;
import WPI.CampusMap.Frontend.Graphics.RouteImageCreator;

public class PrintJob implements Printable
{
	private Path path;
	private Route route;
	
	private RouteImageCreator images;
	
	public PrintJob(Path path)
	{
		this.path = path;
		this.route = new Route(path);
		this.images = new RouteImageCreator(path);
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException 
	{
		if (pageIndex > images.getImages().length - 1)
	         return NO_SUCH_PAGE;
		
		Graphics2D gfx2d = (Graphics2D) graphics;
		gfx2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Image image = images.getImages()[pageIndex];

		float aspectRatio = (float)image.getWidth(null) / (float)image.getHeight(null);
		float xPos = (float) pageFormat.getImageableX() + 30;
		float yPos = (float) pageFormat.getImageableY() + 30;
		float newWidth = (float)pageFormat.getImageableWidth() - 60;
		float newHeight = (1.0f / aspectRatio) * newWidth;
		
	    graphics.drawImage(image, (int)xPos, (int)yPos, (int)newWidth, (int)newHeight, null);
	    
	    yPos += newHeight + 30;
	    
	    int count = 0;
	    for(Instruction instruction : route.getRoute(path.getSection(pageIndex)))
	    {
	    	String line = String.format("%d) %s", count + 1, instruction.getInstruction());
	    	graphics.drawString(line, (int)xPos, (int)yPos);
	    	count++;
	    	yPos += 20;
	    	
	    	if(yPos > pageFormat.getHeight() - 30)
	    	{
	    		yPos = newHeight + 60;
	    		xPos += 200;
	    	}
	    }

	    // tell the caller that this page is part
	    // of the printed document
	    return PAGE_EXISTS;
	}

}
