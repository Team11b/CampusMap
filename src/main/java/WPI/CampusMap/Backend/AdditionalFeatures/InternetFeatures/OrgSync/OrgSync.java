package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.OrgSync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;


public class OrgSync {
	static String url = "https://api.orgsync.com/api/v3/communities/412/events";
	static String charset = java.nio.charset.StandardCharsets.UTF_8.name(); 
	static String key = "H1PfGEPvTHDDtQuXnX6-bQU6cOY63KPAV3t0uwaQ1eU";
	static String perPage = "20";
	static String startTime;
	static String endTime;

	static LinkedList<Event> loadDay(Date date){
		
		startTime = date.getYear()+"-"+date.getMonth()+"-" +date.getDay()+"T00:00:00Z";
		endTime = date.getYear()+"-"+date.getMonth()+"-" +date.getDay()+"T23:59:59Z";
		boolean upcoming = date.after(Calendar.getInstance().getTime());
		String query = null;
		try {
			 query= String.format("key=%s&start_date=%s&end_date=%s&upcoming=%s&per_page=%s", 
			     URLEncoder.encode(key, charset), 
			     URLEncoder.encode(startTime, charset), 
			     URLEncoder.encode(endTime, charset),
			     URLEncoder.encode(upcoming+"", charset),
			     URLEncoder.encode(perPage, charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		URLConnection connection = null;
		try {
			connection = new URL(url + "?" + query).openConnection();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = null;
		try {
			response = connection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader streamReader = null;
		try {
			streamReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		try {
			while ((inputStr = streamReader.readLine()) != null){
			    responseStrBuilder.append(inputStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject returned = new JSONObject(responseStrBuilder.toString());
		
		System.out.println(returned.toString());
		
		return parseJSON(returned);
	}

	private static LinkedList<Event> parseJSON(JSONObject json) {
		LinkedList<Event> events = new LinkedList<Event>();
		
		JSONArray jsonEvents = json.getJSONArray("data");
		System.out.printf("Found %d events\n", jsonEvents.length());
		for(int i = 0; i < jsonEvents.length(); i++){
			JSONObject event = (JSONObject) jsonEvents.get(i);
			String title = event.getString("title");
			String description = event.getString("description");
			String link =  event.getJSONObject("links").getString("web");
			String location = "";
			if(!event.get("location").toString().equals("null")){
				location = (String) event.get("location");
			}else{
				System.out.println("Found null location, event name :"+title);
			}
			String start = event.getJSONArray("dates").getJSONObject(0).getString("starts_at");
			String end = event.getJSONArray("dates").getJSONObject(0).getString("starts_at");
			String organizer = event.getJSONObject("portal").getString("name");
			
			events.add(new Event(title, description, link, location, start, end, organizer));
		}
		
		return events;
	}
}
