package demolition;

import java.util.ArrayList;

import processing.core.*;

public class Bomb {
	
	public static ArrayList<Bomb> bombs = new ArrayList<Bomb>();	
	public int x, y;
	public int explosionStartX, explosionEndX, explosionStartY, explosionEndY;
	public PImage[] sprites;
	public PImage[] explosionSprites;
	public int animationOffset, deltaFrames;
	
	
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
	
	public void animate() {
		deltaFrames++;
		if(deltaFrames % 15 == 0) {
			// The animation frame changes every 0.25 seconds.
			animationOffset++;
		}
	}
	
	public void swapMapChar(int x, int y, char c) {
		char[] rowChars = Map.maps[Map.currentLevel].mapStrings[y].toCharArray();
		rowChars[x] = c;
		Map.maps[Map.currentLevel].mapStrings[y] = String.valueOf(rowChars);
	}
	
	public void collide() {
		
		for(int i = -2; i <= 2; i++) {
			//Horizontal collision
			if(i < 0 && x + i >= 0) {
				if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'W') {
					//If a solid wall.
					explosionStartX = i + 1;
				} else if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'B') {
					//If a broken wall.
					explosionStartX = i;
					swapMapChar(x + i, y, ' ');
				}
			} else if(i > 0 && x + i < Map.maps[Map.currentLevel].mapStrings[0].length()) {
				if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'W') {
					//If a solid wall.
					explosionEndX = i - 1;
					break;
				} else if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x + i) == 'B') {
					//If a broken wall.
					explosionEndX = i;
					swapMapChar(x + i, y, ' ');
					break;
				} 
			}
		}
		
		for(int i = -2; i <= 2; i++) {
			//Vertical collision
			if(i < 0 && y + i >= 0) {
				if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'W') {
					//If a solid wall.
					explosionStartY = i + 1;
				} else if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'B') {
					//If a broken wall.
					explosionStartY = i;
					swapMapChar(x, y + i, ' ');
				}
			} else if(i > 0 && y + i < Map.maps[Map.currentLevel].mapStrings.length) {
				if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'W') {
					//If a solid wall.
					explosionEndY = i - 1;
					break;
				}else if(Map.maps[Map.currentLevel].mapStrings[y + i].charAt(x) == 'B') {
					//If a broken wall.
					explosionEndY = i;
					swapMapChar(x, y + i, ' ');
					break;
				}
			}
		}
	}
	
	public void update() {
		animate();
		if(deltaFrames == 120) {
			collide();
		}
	}
	
	public void draw(PApplet parent) {
		if(animationOffset < 8) {
			parent.image(sprites[animationOffset], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Map.Y_OFFSET);
		} else if(deltaFrames < 150) {
			//Centre sprite.
			parent.image(explosionSprites[0], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Map.Y_OFFSET);
			//row
			for(int i = explosionStartX; i <= explosionEndX; i++) {
				if(i != 0) {
					parent.image(explosionSprites[1], (x + i) * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Map.Y_OFFSET);
				}
			}
			//column
			for(int i = explosionStartY; i <= explosionEndY; i++) {
				if(i != 0) {
					parent.image(explosionSprites[2], x * Map.TILE_WIDTH, (y + i) * Map.TILE_WIDTH + Map.Y_OFFSET);
				}
			}
		}
	}
}
