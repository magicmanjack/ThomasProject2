package demolition;

import java.util.HashMap;

import processing.core.*;
import processing.data.*;

public class Map {
	
	public static final int Y_OFFSET = 64, TILE_WIDTH = 32;
	
	public static Map[] maps;
	public static Entity[] enemies = new Entity[0];
	
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
					addEnemy(new RedEnemy(parent, x, y));
				}
				if((char)(this.mapStrings[y].charAt(x)) == 'Y') {
					addEnemy(new YellowEnemy(parent, x, y));
				}
			}
		}
		this.time = time;
	}
	
	public static void addEnemy(Entity e) {
		Entity[] tempArray = new Entity[enemies.length + 1];
		System.arraycopy(enemies, 0, tempArray, 0, enemies.length);
		enemies = tempArray;
		enemies[enemies.length - 1] = e;
	}
	
	public static void loadMaps(PApplet parent, String configURL) {
		currentLevel = 0; // Sets the current level to the first level.
		
		JSONArray mapObjects = parent.loadJSONObject(configURL).getJSONArray("levels");
		maps = new Map[mapObjects.size()];
		for(int i = 0; i < mapObjects.size(); i++) {
			JSONObject mapObject = mapObjects.getJSONObject(i);
			maps[i] = new Map(parent, parent.loadStrings(mapObject.getString("path")), mapObject.getInt("time"));
		}
		
		bombGuy = new Player(parent, maps[currentLevel].spawnX, maps[currentLevel].spawnY); // Creates the player and sets its position to the first maps spawn.
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
	
	public void draw(PApplet parent) {
		for(int i = 0; i < mapStrings.length; i++) {
			for(int j = 0; j < mapStrings[i].length(); j++) {
				parent.image(tiles.get((char)mapStrings[i].charAt(j)), j * TILE_WIDTH, i * TILE_WIDTH + Y_OFFSET, TILE_WIDTH, TILE_WIDTH);
			}
		}
	}

}
