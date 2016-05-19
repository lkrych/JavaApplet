package guiModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	UnfoldingMap map;
	Map<String,Float> lifeExpByCountry;
	
	List<Feature> countries; 
	List<Marker> countryMarkers;
	
	public void setup(){
		size(800,600,OPENGL);
		map = new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
		
		MapUtils.createDefaultEventDispatcher(this, map); //allows user to interact with map
		
		lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBank.csv"); //don't clutter with method details, create private helper method
		
		countries = GeoJSONReader.loadData(this, 
				"countries.geo.json"); //use helper methods from Unfolding methods
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	public void draw(){
		map.draw(); //calls map method
	}
	
	private void shadeCountries(){
		for (Marker marker : countryMarkers){
			String countryId = marker.getId();
			
			if(lifeExpByCountry.containsKey(countryId)) {
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLevel = (int) map(lifeExp,40,90,10,255); //map is method that translated range of float to color (range min, range max, range new min, range new max)
				marker.setColor(color(255-colorLevel,100,colorLevel)); //low life expectancy will be red, high will be blue.
			}
			else{
				marker.setColor(color(150,150,150)); // if data doesn't exist set grey
				
				
			}
		}
	}
		
	

//helper method that reads data from csv and assigns to HashMap
	private Map<String,Float>
		loadLifeExpectancyFromCSV(String fileName){
		
		Map<String, Float> lifeExpMap
			= new HashMap<String,Float>(); //constructor!
		
		String[] rows = loadStrings(fileName);
		
		for (String row : rows) {
			
			String[] columns = row.split(",");
			if(columns.length == 6 && !columns[5].equals("..")) {
				float value = Float.parseFloat(columns[5]);
				lifeExpMap.put(columns[4], value);
				
			}
		
		}
		return lifeExpMap;
	}
}
