package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support;

public class NoLocation implements ILocation {

	public NoLocation() {
	}

	@Override
	public boolean locationFound() {
		return false;
	}

}
