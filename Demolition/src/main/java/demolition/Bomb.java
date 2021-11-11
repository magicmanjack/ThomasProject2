package demolition;

import java.util.ArrayList;

import processing.core.*;

/** The bomb class
 * This class is the template for any bomb objects. It deals with the updates, rendering and
 * animation of the bombs. */
public class Bomb extends Entity {
	
	/**Stores all instances of the bomb objects */
	public static ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	/**Keeps track of how far the explosion animation travels. */
	public int explosionStartX, explosionEndX, explosionStartY, explosionEndY;
	/**Contains the sprites of the first part of the animation.*/
	public PImage[] sprites;
	/**Contains the sprites of the second explosion part of the animation.*/
	public PImage[] explosionSprites;
	/**The current animation texture.
	 * This is the index of the current texture in the animation sequence*/
	public int animationOffset;
	/**The elapsed frames since the game had started.*
	 * This is used for animation purposes.*/
	public int deltaFrames;
	
	/**The bomb constructor*
	 * Creates a new bomb and initializes its main data fields. Takes in a PApplet parent argument for the purposes of
	 * loading the bombs animation textures. The spawnX and spawnY arguments are for where the bomb spawns.*/
	public Bomb(PApplet parent, int spawnX, int spawnY) {
		x = spawnX;
		y = spawnY;
		explosionStartX = -2;
		explosionEndX = 2;
		explosionStartY = -2;
		explosionEndY = 2;
		sprites = new PImage[8];
		sprites[0] = parent.loadImage("src/main/resources/bomb/bomb1.png");
		sprites[1] = parent.loadImage("src/main/resources/bomb/bomb2.png");
		sprites[2] = parent.loadImage("src/main/resources/bomb/bomb3.png");
		sprites[3] = parent.loadImage("src/main/resources/bomb/bomb4.png");
		sprites[4] = parent.loadImage("src/main/resources/bomb/bomb5.png");
		sprites[5] = parent.loadImage("src/main/resources/bomb/bomb6.png");
		sprites[6] = parent.loadImage("src/main/resources/bomb/bomb7.png");
		sprites[7] = parent.loadImage("src/main/resources/bomb/bomb8.png");
		explosionSprites = new PImage[3];
		explosionSprites[0] = parent.loadImage("src/main/resources/explosion/centre.png");
		explosionSprites[1] = parent.loadImage("src/main/resources/explosion/horizontal.png");
		explosionSprites[2] = parent.loadImage("src/main/resources/explosion/vertical.png");
	}
	
	/** Calculates the animation offset*/
	public void animate() {
		deltaFrames++;
		if(deltaFrames % 15 == 0) {
			// The animation frame changes every 0.25 seconds.
			animationOffset++;
		}
	}
	
	/**Swaps a char in the current map.
	 * The char at position (x, y) in the current map is swapped with the char c.*/
	public void swapMapChar(int x, int y, char c) {
		char[] rowChars = Map.maps[Map.currentLevel].mapStrings[y].toCharArray();
		rowChars[x] = c;
		Map.maps[Map.currentLevel].mapStrings[y] = String.valueOf(rowChars);
	}
	
	/** Deals with the collision with the bomb and other entities.
	 * If the explosion hits any enemies or the player, they are removed. If the bomb explosion
	 * hits any walls, the case is dealt with accordingly. The method takes a collision argument because in some cases,
	 * the map needs to be reloaded.*/
	public void collide(PApplet parent) {
		if(deltaFrames >= 120 && deltaFrames <= 150) { //If two seconds have passed and still within the time line of the explosion animation.
			for(int i = -2; i <= 2; i++) { 
				// For every possible horizontal position of the explosion decide where the explosion starts and ends. Collision with entities are dealt with.
				if(deltaFrames == 120) { // Ensures the explosion collision only happens once at the start of the explosion.
					if(i < 0 && x + i >= 0) {
						if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'W') { //If a solid wall.
							explosionStartX = i + 1;
						} else if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'B') { //If a broken wall.
							explosionStartX = i;
							swapMapChar(x + i, y, ' ');
						}
					} else if(i > 0 && x + i < Map.maps[Map.currentLevel].mapStrings[0].length()) {
						if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'W') { //If a solid wall.
							explosionEndX = i - 1;
							break;
						} else if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'B') { //If a broken wall.
							explosionEndX = i;
							swapMapChar(x + i, y, ' ');
							break;
						} 
					}
				}
				// Collision with enemies.
				for(int j = 0; j < Map.enemies.size(); j++) {
					Enemy e = Map.enemies.get(j);
					if(x + i == e.x && y == e.y) {
						Map.enemies.remove(j);
					}
				}
				// Collision with player.
				if(x + i == Map.bombGuy.x && y == Map.bombGuy.y) {
					Map.bombGuy.life--;
					Map.reloadMap(parent, Map.configURL);
					return;
				}
			}
			
			for(int i = -2; i <= 2; i++) {
				// For every possible vertical position in the explosion, collision with entities are dealt with.
				if(deltaFrames == 120) {
					if(i < 0 && y + i >= 0) {
						if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'W') { //If a solid wall.
							explosionStartY = i + 1;
						} else if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'B') { //If a broken wall.
							explosionStartY = i;
							swapMapChar(x, y + i, ' ');
						}
					} else if(i > 0 && y + i < Map.maps[Map.currentLevel].mapStrings.length) {
						if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'W') { //If a solid wall.
							explosionEndY = i - 1;
							break;
						}else if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'B') { //If a broken wall.
							explosionEndY = i;
							swapMapChar(x, y + i, ' ');
							break;
						}
					}
				}
				// Collision with enemies.
				for(int j = 0; j < Map.enemies.size(); j++) {
					Enemy e = Map.enemies.get(j);
					if(x == e.x && y + i == e.y) {
						Map.enemies.remove(j);
					}
				}
				// Collision with player.
				if(x == Map.bombGuy.x && y + i == Map.bombGuy.y) {
					Map.bombGuy.life--;
					Map.reloadMap(parent, Map.configURL);
					return;
				}
			}
		}
	}
	
	/**Updates the bomb.*/
	@Override
	public void update(PApplet parent) {
		animate();
		collide(parent);
	}
	
	/**Draws the bomb.*/
	@Override
	public void draw(PApplet parent) {
		if(animationOffset < 8) { // If the animation is in the first stage.
			parent.image(sprites[animationOffset], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Map.Y_OFFSET);
		} else if(deltaFrames < 150) { // If the animation is in the explosion stage.
			// Centre sprite.
			parent.image(explosionSprites[0], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Map.Y_OFFSET);
			// Drawing of the explosion row
			for(int i = explosionStartX; i <= explosionEndX; i++) {
				if(i != 0) {
					parent.image(explosionSprites[1], (x + i) * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Map.Y_OFFSET);
				}
			}
			// Drawing of the explosion column
			for(int i = explosionStartY; i <= explosionEndY; i++) {
				if(i != 0) {
					parent.image(explosionSprites[2], x * Map.TILE_WIDTH, (y + i) * Map.TILE_WIDTH + Map.Y_OFFSET);
				}
			}
		}
	}
}
