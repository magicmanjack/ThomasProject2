package demolition;

import java.util.HashMap;

import processing.core.*;
import processing.data.*;

public class Map {
	
	public static final int Y_OFFSET = 64, TILE_WIDTH = 32;
	
	public static Map[] maps;
	
	public static HashMap<Character, PImage> tiles = new HashMap<>();
	
	public String[] mapStrings;
	public int time;
	
	public Map(String[] mapStrings, int time) {
		this.mapStrings = mapStrings;
		this.time = time;
	}
	
	public static void loadMaps(PApplet parent, String configURL) {
		JSONArray mapObjects = parent.loadJSONObject(configURL).getJSONArray("levels");
		maps = new Map[mapObjects.size()];
		for(int i = 0; i < mapObjects.size(); i++) {
			JSONObject mapObject = mapObjects.getJSONObject(i);
			maps[i] = new Map(parent.loadStrings(mapObject.getString("path")), mapObject.getInt("time"));
		}
		
		//Loading images
		tiles.put('W', parent.loadImage("src/main/resources/wall/solid.png"));
		tiles.put('B', parent.loadImage("src/main/resources/broken/broken.png"));
		tiles.put(' ', parent.loadImage("src/main/resources/empty/empty.png"));
		tiles.put('G', parent.loadImage("src/main/resources/goal/goal.png"));
		tiles.put('P', parent.loadImage("src/main/resources/empty/empty.png"));
		tiles.put('Y', parent.loadImage("src/main/resources/empty/empty.png"));
		tiles.put('R', parent.loadImage("src/main/resources/empty/empty.png"));
	}
	
	public void draw(PApplet parent) {
		for(int i = 0; i < mapStrings.length; i++) {
			for(int j = 0; j < mapStrings[i].length(); j++) {
				parent.image(tiles.get((char)mapStrings[i].charAt(j)), j * TILE_WIDTH, i * TILE_WIDTH + Y_OFFSET, TILE_WIDTH, TILE_WIDTH);
			}
		}
	}

}
