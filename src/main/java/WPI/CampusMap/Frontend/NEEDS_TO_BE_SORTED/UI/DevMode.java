package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics2D;

public class DevMode extends UIMode 
{
	
	//Singleton
	private static DevMode instance;
	
	public static DevMode getInstance()
	{
		return null;
	}
	
	private String currentEditMode;
	private String pointID;
	private String pointType;
	
	public static final String SELECT_MODE = "selectmode";
	public static final String PLACE_MODE = "placemode";
	public static final String REMOVE_MODE = "removemode";
	public static final String EDGE_MODE = "edgemode";
	public static final String REMOVE_EDGE_MODE = "removeedgemode";
	
	public DevMode(AppMainWindow window)
	{
		super(window);
		
		instance = this;
		currentEditMode = SELECT_MODE;
	}
	
	@Override
	public void onModeEntered(){
		//UIMode.switchCurrentMode();
		currentEditMode = SELECT_MODE;
		//UIMode.setWindowText("Dev Mode");		
		//Switch label to textbox for scale
		//Show and hide UI elements
	}
	
	

	public void setPlace(){
		System.out.println("Place/create");
		if(currentEditMode != PLACE_MODE){
			currentEditMode = PLACE_MODE;
		}
		else{				
			currentEditMode = SELECT_MODE;
		}
		
		clearNodeInfo();
		/*if(mapPanel.getDevMode() != EditorToolMode.Point){
			mapPanel.setDevMode(EditorToolMode.Point);
		}
		else{
		mapPanel.setDevMode(EditorToolMode.None);				
		}
	
		btnDelNode.setSelected(false);
		btnRemoveEdge.setSelected(false);
		btnEdgeMode.setSelected(false);*/
			
			
	}
	
	public void setRemove(){
		System.out.println("Delete/remove");
		if(currentEditMode != REMOVE_MODE){
			currentEditMode = REMOVE_MODE;
		}
		else{				
			currentEditMode = SELECT_MODE;
		}
		clearNodeInfo();
		/*if(mapPanel.getDevMode() != EditorToolMode.DeletePoint){
		mapPanel.setDevMode(EditorToolMode.DeletePoint);
		}
		else{
		mapPanel.setDevMode(EditorToolMode.None);	
		}
		
		btnNode.setSelected(false);
		btnRemoveEdge.setSelected(false);
		btnEdgeMode.setSelected(false);*/
	}
	
	public void setEdge(){
		System.out.println("Edge");
		if(currentEditMode != EDGE_MODE){
			currentEditMode = EDGE_MODE;
		}
		else{				
			currentEditMode = SELECT_MODE;
		}
		
		clearNodeInfo();
		/*if(mapPanel.getDevMode() != EditorToolMode.Edge){
		mapPanel.setDevMode(EditorToolMode.Edge);
		}
		else{
		mapPanel.setDevMode(EditorToolMode.None);
		}
		
		btnNode.setSelected(false);
		btnDelNode.setSelected(false);
		btnRemoveEdge.setSelected(false);*/
	}
	
	public void setRemoveEdge(){
		System.out.println("Delete Edge");
		if(currentEditMode != REMOVE_EDGE_MODE){
			currentEditMode = REMOVE_EDGE_MODE;
		}
		else{				
			currentEditMode = SELECT_MODE;
		}
		clearNodeInfo();
		/*if(mapPanel.getDevMode() != EditorToolMode.DeleteEdge){
		mapPanel.setDevMode(EditorToolMode.DeleteEdge);
		}
		else{
		mapPanel.setDevMode(EditorToolMode.None);
		}

		btnNode.setSelected(false);
		btnDelNode.setSelected(false);
		btnEdgeMode.setSelected(false);*/
	}
	
	public void save(){
		System.out.println("Save");
		if(currentEditMode != SELECT_MODE){
			currentEditMode = SELECT_MODE;
		}
		/*System.out.println(txtScale.getText());
		mapPanel.currentMap.setScale(Float.parseFloat(txtScale.getText()));
		System.out.println("SAVING!");
		
		//clearNodeInfo();
		
		//toggle buttons
		currentDevMode = DevMode.none;				
		System.out.println("null mode");
		btnNode.setSelected(false);
		btnDelNode.setSelected(false);
		btnRemoveEdge.setSelected(false);
		btnEdgeMode.setSelected(false);
		
		Serializer.write(mapPanel.currentMap);
		//XML.writePoints(mapPanel.currentMap);
		lblScale.setText("Scale: " + mapPanel.currentMap.getScale() + " inches per ft");*/
	}
	
	public void setType(String Type){		
		pointType = Type;
		//set the typeselector
	}
	
	public void setID(String Id){
		pointID = Id;
		//set the textbox
		
	}
	
	public String getcurrentEditMode(){
		return currentEditMode; 
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
	public void onDraw(Graphics2D graphics) {
		// TODO Auto-generated method stub
		
	}
}
