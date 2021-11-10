package demolition;

import processing.core.PApplet;
import processing.core.PImage;

public class YellowEnemy extends Enemy {
	public YellowEnemy(PApplet parent, int spawnX, int spawnY) {
		x = spawnX;
		y = spawnY;
		xDir = -1;
		yDir = 0;
		animationStart = 0;
		animationOffset = 0;
		sprites = new PImage[16];
		sprites[0] = parent.loadImage("src/main/resources/yellow_enemy/yellow_left1.png"); 
		sprites[1] = parent.loadImage("src/main/resources/yellow_enemy/yellow_left2.png");
		sprites[2] = parent.loadImage("src/main/resources/yellow_enemy/yellow_left3.png");
		sprites[3] = parent.loadImage("src/main/resources/yellow_enemy/yellow_left4.png"); 
		sprites[4] = parent.loadImage("src/main/resources/yellow_enemy/yellow_right1.png");
		sprites[5] = parent.loadImage("src/main/resources/yellow_enemy/yellow_right2.png");
		sprites[6] = parent.loadImage("src/main/resources/yellow_enemy/yellow_right3.png");
		sprites[7] = parent.loadImage("src/main/resources/yellow_enemy/yellow_right4.png");
		sprites[8] = parent.loadImage("src/main/resources/yellow_enemy/yellow_up1.png");
		sprites[9] = parent.loadImage("src/main/resources/yellow_enemy/yellow_up2.png");
		sprites[10] = parent.loadImage("src/main/resources/yellow_enemy/yellow_up3.png");
		sprites[11] = parent.loadImage("src/main/resources/yellow_enemy/yellow_up4.png");
		sprites[12] = parent.loadImage("src/main/resources/yellow_enemy/yellow_down1.png");
		sprites[13] = parent.loadImage("src/main/resources/yellow_enemy/yellow_down2.png");
		sprites[14] = parent.loadImage("src/main/resources/yellow_enemy/yellow_down3.png");
		sprites[15] = parent.loadImage("src/main/resources/yellow_enemy/yellow_down4.png");
	}
	
	@Override
	public void update(PApplet parent) {
		animate();
		if(deltaFrames % 60 == 0) {
			if(collides()) {	
				while(collides()) {
					if(xDir == -1 && yDir == 0) {
						xDir = 0;
						yDir = -1;
						animationStart = 8;
					} else if(xDir == 1 && yDir == 0) {
						xDir = 0;
						yDir = 1;
						animationStart = 12;
					} else if(xDir == 0 && yDir == -1) {
						xDir = 1;
						yDir = 0;
						animationStart = 4;
					} else if(xDir == 0 && yDir == 1) {
						xDir = -1;
						yDir = 0;
						animationStart = 0;
					}
				}
			}
			x += xDir;
			y += yDir;
		}
		// Player collision
		if(x == Map.bombGuy.x && y == Map.bombGuy.y) {
			Map.bombGuy.life--;
			Map.reloadMap(parent, Map.configURL);
		}
	}
}
