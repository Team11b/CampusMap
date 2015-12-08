package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.ILocation;

public class NoLocation implements ILocation {

	public NoLocation() {
	}

	@Override
	public boolean locationFound() {
		return false;
	}

}
