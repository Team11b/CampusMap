package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import org.apache.commons.lang3.NotImplementedException;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap;

import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevGraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap.GraphicsBatchComparator;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.UserGraphicalMap;


public class DevMode extends UIMode 
{	
	private DevGraphicalMap graphicsMap;
	protected IMap currentMap;
	
	public static DevMode getInstance()
	{
		return null;
	}	
	
	private String pointID;
	private String pointType;	

	
	public DevMode(AppMainWindow window)
	{
		super(window);
		setSelect();
	}
	
	@Override
	public void onModeEntered(){		
		
		//UIMode.setWindowText("Dev Mode");		
		//Switch label to textbox for scale
		//Show and hide UI elements
		//if(currentMap != null)
			//graphicsMap = new DevGraphicalMap(currentMap, this);
		graphicsMap.setToolMode(EditorToolMode.None);
			//throw new NotImplementedException("getMap?");
	}
	
	@Override
	public void gotoPoint(String name) 
	{
	}
	
	public void setSelect(){		
		System.out.println("Select Mode");
		graphicsMap.setToolMode(EditorToolMode.None);
	}

	public void setPlace(){
		System.out.println("Place/create");
		clearNodeInfo();		
				
		graphicsMap.setToolMode(EditorToolMode.Point);					
	}
	
	public void setRemove(){
		System.out.println("Delete/remove");
		clearNodeInfo();
		
		graphicsMap.setToolMode(EditorToolMode.DeletePoint);			
	}
	
	public void setEdge(){		
		System.out.println("Edge");
		clearNodeInfo();
		
		graphicsMap.setToolMode(EditorToolMode.Edge);				
	}
	
	public void setRemoveEdge(){
		System.out.println("Delete Edge");
		clearNodeInfo();
		
		graphicsMap.setToolMode(EditorToolMode.DeleteEdge);			
	}
	
	public void save(){
		System.out.println("Save");
		graphicsMap.setToolMode(EditorToolMode.None);		
		
		clearNodeInfo();		
		
		currentMap.save();
		//lblScale.setText("Scale: " + currentMap.getScale() + " inches per ft");
	}
	
	public void setType(String Type){		
		pointType = Type;
		//set the typeselector
	}
	
	public void setID(String Id){
		pointID = Id;
		//set the textbox
		
	}
	
	public EditorToolMode getcurrentEditMode(){
		return graphicsMap.getToolMode();
	}
	
	public String getType(){
		//pull from typeselector
		return pointType;
	}
	
	public String getID(){
		//pull from idtextbox
		return pointID;
	}
	
	public void updateTypeID(){
		//for saving and switching points after change
	}
	
	public void addConnect(){
		
	}
	
	private void clearNodeInfo(){
		//typeSelector.setSelectedIndex(0);
		//nodeTextField.setText("");
	}

	@Override
	public final void onDraw(Graphics2D graphics)
	{		
		System.out.println("On Draw");
		if(graphicsMap != null)
			graphicsMap.onDraw(graphics);
		/*graphics.clearRect(0, 0, panel.getWidth(), panel.getWidth());
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics.setTransform(transform);

		if (map == null)
			return;
		
		synchronized (this)
		{
			graphics.setColor(Color.white);
			graphics.drawImage(map.getLoadedImage().getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
			
			batchList.sort(new GraphicsBatchComparator());
			for(int i = 0; i < batchList.size(); i++)
			{
				GraphicsObject<?, ?> go = batchList.get(i);
				if(go.isDelelted())
				{
					batchList.remove(i);
					
					i--;
				}
				else
				{
					graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, go.getAlpha()));
					graphics.setColor(go.getColor());
					go.onDraw(graphics);
				}
			}
		}*/		
	}

	@Override
	public void onMouseClickMap(MouseEvent e) {
		if(graphicsMap != null)
			graphicsMap.mouseClick(e);	
		
	}

	@Override
	public void onMouseEnterMap(MouseEvent e) {
		// TODO Auto-generated method stub
		if(graphicsMap != null)
			graphicsMap.mouseEnter(e);
	}

	@Override
	public void onMouseLeaveMap(MouseEvent e) {
		
		if(graphicsMap != null)
			graphicsMap.mouseExit(e);		
		
	}

	@Override
	public void onMouseMoveOverMap(MouseEvent e) {
		if(graphicsMap != null)
			graphicsMap.mouseMove(e);
	}

	@Override
	public void onMouseDraggedOverMap(MouseEvent e) {
		if(graphicsMap != null)		
			graphicsMap.mouseDrag(e);		
		
	}

	@Override
	public void loadMap(String mapName){
		System.out.println("UI: " + mapName);
		
		synchronized(this)
		{
			System.out.println(mapName);
		
			currentMap = AllMaps.getInstance().getMap(mapName);			
			
			if(graphicsMap != null)
				graphicsMap.unload();
			graphicsMap = new DevGraphicalMap(mapName, this);
			graphicsMap.spawnMap();			
			
		}
	}
}
