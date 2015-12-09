package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.WeatherAnalysis;

public class WeatherHeuristicProcessor extends NodeProcessor {
	private LocationPref pref;
	
	//TODO check that this is right
	private static final String campus = "CampusMap";
	
	private static final float modifier = (float) 100.0;
	

	public WeatherHeuristicProcessor() {
	}
	
	public WeatherHeuristicProcessor(NodeProcessor child, LocationPref pref) {
		super(child);
		this.pref = pref;
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		if (pref == LocationPref.WEATHER) {
			node.modifyHeuristicCost((float)WeatherAnalysis.getWeatherScore());
		}
		else if (pref == LocationPref.OUTSIDE) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
				node.modifyHeuristicCost(WeatherHeuristicProcessor.modifier);
			}
			else {
				node.modifyHeuristicCost((-1) * WeatherHeuristicProcessor.modifier);
			}
		}
		else if (pref == LocationPref.INSIDE) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
				node.modifyHeuristicCost((-1) * WeatherHeuristicProcessor.modifier);
			}
			else {
				node.modifyHeuristicCost(WeatherHeuristicProcessor.modifier);
			}
		}

	}

}
