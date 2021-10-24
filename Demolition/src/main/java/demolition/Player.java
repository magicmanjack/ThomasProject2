package demolition;

import processing.core.*;

public class Player {
	
	public static final int SPRITE_OFFSET_Y = -16;
	
	public int x, y, xDir, yDir;
	public int animationStart, animationOffset, deltaFrames;
	public PImage[] sprites;
	
	public Player(PApplet parent, int spawnX, int spawnY) {
		x = spawnX;
		y = spawnY;
		animationStart = 12;
		animationOffset = 0;
		sprites = new PImage[16];
		sprites[0] = parent.loadImage("src/main/resources/player/player_left1.png"); 
		sprites[1] = parent.loadImage("src/main/resources/player/player_left2.png");
		sprites[2] = parent.loadImage("src/main/resources/player/player_left3.png");
		sprites[3] = parent.loadImage("src/main/resources/player/player_left4.png"); 
		sprites[4] = parent.loadImage("src/main/resources/player/player_right1.png");
		sprites[5] = parent.loadImage("src/main/resources/player/player_right2.png");
		sprites[6] = parent.loadImage("src/main/resources/player/player_right3.png");
		sprites[7] = parent.loadImage("src/main/resources/player/player_right4.png");
		sprites[8] = parent.loadImage("src/main/resources/player/player_up1.png");
		sprites[9] = parent.loadImage("src/main/resources/player/player_up2.png");
		sprites[10] = parent.loadImage("src/main/resources/player/player_up3.png");
		sprites[11] = parent.loadImage("src/main/resources/player/player_up4.png");
		sprites[12] = parent.loadImage("src/main/resources/player/player1.png");
		sprites[13] = parent.loadImage("src/main/resources/player/player2.png");
		sprites[14] = parent.loadImage("src/main/resources/player/player3.png");
		sprites[15] = parent.loadImage("src/main/resources/player/player4.png");
		
	}
	
	public void animate() {
		animationOffset++;
		if(animationOffset > 3) {
			animationOffset = 0;
		}
	}
	
	public void update() {
		deltaFrames++;
		if(deltaFrames >= 12) {
			deltaFrames = 0;
			// The animation frame changes every 0.2 seconds.
			animate();
		}
		if((char)Map.maps[Map.currentLevel].mapStrings[y + yDir].charAt(x + xDir) == 'W' 
				|| (char)Map.maps[Map.currentLevel].mapStrings[y + yDir].charAt(x + xDir) == 'B') {
			xDir = 0;
			yDir = 0;
		}
		x += xDir;
		y += yDir;
		xDir = 0;
		yDir = 0;
	}
	
	public void draw(PApplet parent) {
		parent.image(sprites[animationStart + animationOffset], x * Map.TILE_WIDTH, y * Map.TILE_WIDTH + Player.SPRITE_OFFSET_Y + Map.Y_OFFSET);
	}

}
