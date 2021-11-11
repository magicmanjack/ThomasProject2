package demolition;

import processing.core.PApplet;
import processing.core.PImage;

/** The enemy class.
 * Defines an enemy object which has a unique animation and collision detection.*/
public class Enemy extends Entity {
	
	/** The y offset the sprite. 
	 * This is the offset that the sprite is drawn relative to the position of the enemy.*/
	public static final int SPRITE_OFFSET_Y = -16;
	
	/** The direction of the enemy.*/
	public int xDir, yDir;
	
	/** Used for the the sprite animations.*/
	public int animationStart, animationOffset, deltaFrames;
	/** Contains the sprites.*/
	public PImage[] sprites;
	
	/** Deals with the animation variables.*/
	public void animate() {
		deltaFrames++;
		if(deltaFrames % 12 == 0) {
			// The animation frame changes every 0.2 seconds.
			animationOffset++;
			if(animationOffset > 3) {
				animationOffset = 0;
			}
		}
	}
	
	/** Returns true on collision.
	 * If the Enemy collides with a solid or broken wall, this method will return true.*/
	public boolean collides() {
		return ((char)Map.maps[Map.currentLevel].mapStrings[y + yDir].charAt(x + xDir) == 'W' 
				|| (char)Map.maps[Map.currentLevel].mapStrings[y + yDir].charAt(x + xDir) == 'B');
	}
	
	/**Updates the Enemy.*/
	@Override
	public void update(PApplet parent) {
		
	}
	
	/**Draws the Enemy.*/
	@Override
	public void draw(PApplet parent) {
		parent.image(sprites[animationStart + animationOffset], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Player.SPRITE_OFFSET_Y + Map.Y_OFFSET);
	}
}
