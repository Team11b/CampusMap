package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.WeatherAnalysis;

public class WeatherHeuristicProcessor extends NodeProcessor {
	private LocationPref pref;

	private static final String campus = "Campus_Map";

	private static final float modifier = (float) 50.0;
	private static final float scoreDivisor = 100;
	private float score;

	public WeatherHeuristicProcessor() {
	}

	public WeatherHeuristicProcessor(NodeProcessor child, LocationPref pref) {
		super(child);
		this.pref = pref;
		if(pref == LocationPref.WEATHER) this.score = (float) WeatherAnalysis.getWeatherScore();;
	}

	@Override
	protected void onProcessNode(Node node, Node goal) {
		float segment = node.getAccumulatedDistance()-node.getLast().getAccumulatedDistance();
		float previousDist = node.getLast().getAccumulatedDistance();
		
		if (pref == LocationPref.WEATHER) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
				node.modifyHeuristicCost(score);
				node.setAccumulatedDistance(previousDist+ segment *(scoreDivisor + score)/scoreDivisor);
			} else {
//				node.modifyHeuristicCost((-1) * score);
//				node.setAccumulatedDistance(previousDist + segment *(scoreDivisor - score)/scoreDivisor);
			}
		} else if (pref == LocationPref.INSIDE) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
				node.modifyHeuristicCost(WeatherHeuristicProcessor.modifier);
				node.setAccumulatedDistance(previousDist + segment *(scoreDivisor + modifier)/scoreDivisor);
			} else {
//				node.modifyHeuristicCost((-1) * WeatherHeuristicProcessor.modifier);
//				node.setAccumulatedDistance(previousDist + segment *(scoreDivisor - modifier)/scoreDivisor);
			}
		} else if (pref == LocationPref.OUTSIDE) {
			if (node.getPoint().getMap().equals(WeatherHeuristicProcessor.campus)) {
//				node.modifyHeuristicCost((-1) * WeatherHeuristicProcessor.modifier);
//				node.setAccumulatedDistance(previousDist + segment *(scoreDivisor - modifier)/scoreDivisor);
			} else {
				node.modifyHeuristicCost(WeatherHeuristicProcessor.modifier);
				node.setAccumulatedDistance(previousDist + segment *(scoreDivisor + modifier)/scoreDivisor);
			}
		}

	}

}
