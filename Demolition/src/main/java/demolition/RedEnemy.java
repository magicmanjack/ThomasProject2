package demolition;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class RedEnemy extends Entity {
	
	public RedEnemy(PApplet parent, int spawnX, int spawnY) {
		x = spawnX;
		y = spawnY;
		xDir = 1;
		yDir = 0;
		animationStart = 4;
		animationOffset = 0;
		sprites = new PImage[16];
		sprites[0] = parent.loadImage("src/main/resources/red_enemy/red_left1.png"); 
		sprites[1] = parent.loadImage("src/main/resources/red_enemy/red_left2.png");
		sprites[2] = parent.loadImage("src/main/resources/red_enemy/red_left3.png");
		sprites[3] = parent.loadImage("src/main/resources/red_enemy/red_left4.png"); 
		sprites[4] = parent.loadImage("src/main/resources/red_enemy/red_right1.png");
		sprites[5] = parent.loadImage("src/main/resources/red_enemy/red_right2.png");
		sprites[6] = parent.loadImage("src/main/resources/red_enemy/red_right3.png");
		sprites[7] = parent.loadImage("src/main/resources/red_enemy/red_right4.png");
		sprites[8] = parent.loadImage("src/main/resources/red_enemy/red_up1.png");
		sprites[9] = parent.loadImage("src/main/resources/red_enemy/red_up2.png");
		sprites[10] = parent.loadImage("src/main/resources/red_enemy/red_up3.png");
		sprites[11] = parent.loadImage("src/main/resources/red_enemy/red_up4.png");
		sprites[12] = parent.loadImage("src/main/resources/red_enemy/red_down1.png");
		sprites[13] = parent.loadImage("src/main/resources/red_enemy/red_down2.png");
		sprites[14] = parent.loadImage("src/main/resources/red_enemy/red_down3.png");
		sprites[15] = parent.loadImage("src/main/resources/red_enemy/red_down4.png");
	}
	
	@Override
	public void update() {
		animate();
		if(deltaFrames % 60 == 0) {
			if(collides()) {
				String freeSpaces = "";
				//Check up
				xDir = 0;
				yDir = -1;
				if(!collides()) {
					freeSpaces = freeSpaces + "u";
				}
				
				//Check down
				xDir = 0;
				yDir = 1;
				if(!collides()) {
					freeSpaces = freeSpaces + "d";
				}
				
				//Check left
				xDir = -1;
				yDir = 0;
				if(!collides()) {
					freeSpaces = freeSpaces + "l";
				}
				
				//Check right
				xDir = 1;
				yDir = 0;
				if(!collides()) {
					freeSpaces = freeSpaces + "r";
				}
				
				Random rand = new Random();
				switch((char)freeSpaces.charAt(rand.nextInt(freeSpaces.length()))) {
					case 'u':
						xDir = 0;
						yDir = -1;
						animationStart = 8;
						break;
					case 'd':
						xDir = 0;
						yDir = 1;
						animationStart = 12;
						break;
					case 'l':
						xDir = -1;
						yDir = 0;
						animationStart = 0;
						break;
					case 'r':
						xDir = 1;
						yDir = 0;
						animationStart = 4;
						break;
				}
			}
			x += xDir;
			y += yDir;
		}
	}
}
