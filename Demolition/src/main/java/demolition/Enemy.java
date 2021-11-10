package demolition;

import processing.core.PApplet;
import processing.core.PImage;

public class Enemy extends Entity {
	public static final int SPRITE_OFFSET_Y = -16;
	
	public int xDir, yDir;
	public int animationStart, animationOffset, deltaFrames;
	public PImage[] sprites;
	
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
	
	public boolean collides() {
		return ((char)Map.maps[Map.currentLevel].mapStrings[y + yDir].charAt(x + xDir) == 'W' 
				|| (char)Map.maps[Map.currentLevel].mapStrings[y + yDir].charAt(x + xDir) == 'B');
	}
	
	@Override
	public void update(PApplet parent) {
		
	}
	
	@Override
	public void draw(PApplet parent) {
		parent.image(sprites[animationStart + animationOffset], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Player.SPRITE_OFFSET_Y + Map.Y_OFFSET);
	}
}
