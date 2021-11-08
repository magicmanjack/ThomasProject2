package demolition;

import java.util.*;

import processing.core.*;
import processing.data.*;

public class Map {
	
	public static final int Y_OFFSET = 64, TILE_WIDTH = 32;
	
	public static Map[] maps;
	public static String configURL;
	public static ArrayList<Entity> enemies = new ArrayList<Entity>();
	
	public static HashMap<Character, PImage> tiles;
	
	public static int currentLevel;
	public static Player bombGuy;
	
	public String[] mapStrings;
	public int time;
	public int spawnX, spawnY;
	
	public Map(PApplet parent, String[] mapStrings, int time) {
		this.mapStrings = mapStrings;
		for(int y = 0; y < mapStrings.length; y++) {
			for(int x = 0; x < mapStrings.length; x++) {
				if((char)(this.mapStrings[y].charAt(x)) == 'P') {
					spawnX = x;
					spawnY = y;
				}
				if((char)(this.mapStrings[y].charAt(x)) == 'R') {
					enemies.add(new RedEnemy(parent, x, y));
				}
				if((char)(this.mapStrings[y].charAt(x)) == 'Y') {
					enemies.add(new YellowEnemy(parent, x, y));
				}
			}
		}
		this.time = time;
	}

	public static void loadMaps(PApplet parent, String cURL) {
		currentLevel = 0; // Sets the current level to the first level.
		configURL = cURL;
		JSONArray mapObjects = parent.loadJSONObject(configURL).getJSONArray("levels");
		maps = new Map[mapObjects.size()];
		for(int i = 0; i < mapObjects.size(); i++) {
			JSONObject mapObject = mapObjects.getJSONObject(i);
			maps[i] = new Map(parent, parent.loadStrings(mapObject.getString("path")), mapObject.getInt("time"));
		}
		
		bombGuy = new Player(parent, maps[currentLevel].spawnX, maps[currentLevel].spawnY); // Creates the player and sets its position to the first maps spawn.
		bombGuy.life = parent.loadJSONObject(configURL).getInt("lives");
		
		//Loading images
		tiles = new HashMap<>();
		tiles.put('W', parent.loadImage("src/main/resources/wall/solid.png"));
		tiles.put('B', parent.loadImage("src/main/resources/broken/broken.png"));
		tiles.put(' ', parent.loadImage("src/main/resources/empty/empty.png"));
		tiles.put('G', parent.loadImage("src/main/resources/goal/goal.png"));
		tiles.put('P', parent.loadImage("src/main/resources/empty/empty.png"));
		tiles.put('Y', parent.loadImage("src/main/resources/empty/empty.png"));
		tiles.put('R', parent.loadImage("src/main/resources/empty/empty.png"));
	}
	
	public static void reloadMap(PApplet parent, String configURL) {
		//Clearing the enemies array list.
		enemies.clear();
		//Clearing the bombs array list.
		Bomb.bombs.clear();
		// The level is completely reloaded.
		if(Map.currentLevel < Map.maps.length) {
			int oldTime = Map.maps[Map.currentLevel].time;
			JSONArray mapObjects = parent.loadJSONObject(configURL).getJSONArray("levels");
			JSONObject mapObject = mapObjects.getJSONObject(currentLevel);
			maps[currentLevel] = new Map(parent, parent.loadStrings(mapObject.getString("path")), oldTime);
			// Respawns Bomb guy.
			bombGuy.animationStart = 12;
			bombGuy.x = maps[currentLevel].spawnX;
			bombGuy.y = maps[currentLevel].spawnY;
		}
	}
	
	public void draw(PApplet parent) {
		for(int i = 0; i < mapStrings.length; i++) {
			for(int j = 0; j < mapStrings[i].length(); j++) {
				parent.image(tiles.get((char)mapStrings[i].charAt(j)), j * TILE_WIDTH, i * TILE_WIDTH + Y_OFFSET, TILE_WIDTH, TILE_WIDTH);
			}
		}
	}

}
