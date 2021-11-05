package demolition;

import processing.core.*;

public class Player extends Entity {
	
	public int life;
	public boolean dead;
	
	public Player(PApplet parent, int spawnX, int spawnY) {
		life = 3;
		x = spawnX;
		y = spawnY;
		xDir = 0;
		yDir = 0;
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
	
	@Override
	public void update(PApplet parent) {
		animate();
		if(collides()) {
			xDir = 0;
			yDir = 0;
		}
		x += xDir;
		y += yDir;
		xDir = 0;
		yDir = 0;
		if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x) == 'G') {
			// Touching the goal.
			Map.currentLevel++;
			Map.reloadMap(parent, Map.configURL);
		}
	}

}
