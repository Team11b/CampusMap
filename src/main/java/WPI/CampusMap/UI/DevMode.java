package WPI.CampusMap.UI;

import WPI.CampusMap.Dev.EditorToolMode;
import WPI.CampusMap.Serialization.Serializer;
//import WPI.CampusMap.UI.AppUIObject.DevMode;

public class DevMode extends UIMode {
	//Singleton
	private static DevMode instance;
	
	public static DevMode getInstance()
	{
		return instance;
	}
	
	private String currentMode;
	private String pointID;
	private String pointType;
	
	public static final String SELECT_MODE = "selectmode";
	public static final String PLACE_MODE = "placemode";
	public static final String REMOVE_MODE = "removemode";
	public static final String EDGE_MODE = "edgemode";
	public static final String REMOVE_EDGE_MODE = "removeedgemode";
	
	public DevMode(){
		instance = this;
		currentMode = SELECT_MODE;
	}
	
	@Override
	public void onModeEntered(){
		currentMode = SELECT_MODE;
		//UIMode.setWindowText("Dev Mode");		
		//Switch label to textbox for scale
		//Show and hide UI elements
	}
	
	

	public void setPlace(){
		if(currentMode != PLACE_MODE){
			currentMode = PLACE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
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
		if(currentMode != REMOVE_MODE){
			currentMode = REMOVE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
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
		if(currentMode != EDGE_MODE){
			currentMode = EDGE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
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
		if(currentMode != REMOVE_EDGE_MODE){
			currentMode = REMOVE_EDGE_MODE;
		}
		else{				
			currentMode = SELECT_MODE;
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
		if(currentMode != SELECT_MODE){
			currentMode = SELECT_MODE;
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
	
	public String getCurrentMode(){
		return currentMode; 
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
}
