package demolition;

import java.util.*;

import processing.core.*;
import processing.data.*;

/** The Map class.
 * Contains information about a game map such as its tiles and time limit.*/
public class Map {
	
	/** Used for drawing items with the correct dimensions.*/
	public static final int Y_OFFSET = 64, TILE_WIDTH = 32;
	/**All the games maps.*/
	public static Map[] maps;
	/**The URL to the config file*
	 * This is stored statically so it can be used later by any class to reload map files.*/
	public static String configURL;
	/** A list of enemy objects */
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	/** A dictionary that maps the special map characters to an image.*/
	public static HashMap<Character, PImage> tiles;
	/** The current level that the player is on.*/
	public static int currentLevel;
	/** The player object.
	 * The player is static so it can be accessed by any class.*/
	public static Player bombGuy;
	
	/** The strings loaded in from the map file.*/
	public String[] mapStrings;
	/** The time left the player has.*/
	public int time;
	/** The spawn location of the player.
	 * this may be different for each map.*/
	public int spawnX, spawnY;
	
	/**Constructor.
	 * Sets the local mapStrings and time variables to the arguments. The enemies are added to the
	 * enemies array list.*/ 
	public Map(PApplet parent, String[] mapStrings, int time) {
		this.mapStrings = mapStrings;
		for(int y = 0; y < mapStrings.length; y++) {
			for(int x = 0; x < mapStrings.length; x++) {
				// For every character in the map.
				if((char)(this.mapStrings[y].charAt(x)) == 'P') {
					//The map spawn location is set to the first occurance of the special character 'P'
					spawnX = x;
					spawnY = y;
				}
				if((char)(this.mapStrings[y].charAt(x)) == 'R') {
					//A red enemy is added to the enemies array list every time a 'R' appears.
					enemies.add(new RedEnemy(parent, x, y));
				}
				if((char)(this.mapStrings[y].charAt(x)) == 'Y') {
					//A yellow enemy is added to the enemies array list every time a 'Y' appears.
					enemies.add(new YellowEnemy(parent, x, y));
				}
			}
		}
		this.time = time;
	}

	/** Loads each map from the config file.*/
	public static void loadMaps(PApplet parent, String cURL) {
		currentLevel = 0; // Sets the current level to the first level.
		configURL = cURL; // The URL is saved for later.
		JSONArray mapObjects = parent.loadJSONObject(configURL).getJSONArray("levels");
		maps = new Map[mapObjects.size()];
		for(int i = 0; i < mapObjects.size(); i++) {
			JSONObject mapObject = mapObjects.getJSONObject(i);
			maps[i] = new Map(parent, parent.loadStrings(mapObject.getString("path")), mapObject.getInt("time")); // A new map is created from each JSON object in the config file.
		}
		
		bombGuy = new Player(parent, maps[currentLevel].spawnX, maps[currentLevel].spawnY); // Creates the player and sets its position to the first maps spawn.
		bombGuy.life = parent.loadJSONObject(configURL).getInt("lives"); // Sets the players lives.
		
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
	
	/** Reloads the currentMap.
	 * This method only reloads the current map and not all of the maps. The mapStrings are reloaded back to how they were at the start.
	 * The enemies are reloaded.*/
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
	
	/** Draws each tile of the current map.*/
	public void draw(PApplet parent) {
		for(int i = 0; i < mapStrings.length; i++) {
			for(int j = 0; j < mapStrings[i].length(); j++) {
				parent.image(tiles.get((char)mapStrings[i].charAt(j)), j * TILE_WIDTH, i * TILE_WIDTH + Y_OFFSET, TILE_WIDTH, TILE_WIDTH);
			}
		}
	}

}
