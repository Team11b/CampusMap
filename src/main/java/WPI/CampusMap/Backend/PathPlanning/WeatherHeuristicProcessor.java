package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.WeatherAnalysis;

public class WeatherHeuristicProcessor extends NodeProcessor {
	private boolean usingWeather;
	private boolean outside;
	private boolean pref;
	
	//TODO check that this is right
	private static final String campus = "CampusMap";
	
	private static final float modifier = (float) 100.0;
	

	public WeatherHeuristicProcessor() {
	}
	
	public WeatherHeuristicProcessor(NodeProcessor child, boolean usingWeather, boolean outside, boolean pref) {
		super(child);
		this.usingWeather = usingWeather;
		this.outside = outside;
		this.pref = pref;
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		if (this.usingWeather) {
			node.modifyHeuristicCost((float)WeatherAnalysis.getWeatherScore());
		}
		else if (pref && outside) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
				node.modifyHeuristicCost(WeatherHeuristicProcessor.modifier);
			}
			else {
				node.modifyHeuristicCost((-1) * WeatherHeuristicProcessor.modifier);
			}
		}
		else if (pref && (!(outside))) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
				node.modifyHeuristicCost((-1) * WeatherHeuristicProcessor.modifier);
			}
			else {
				node.modifyHeuristicCost(WeatherHeuristicProcessor.modifier);
			}
		}

	}

}
