package demolition;

import processing.core.PApplet;
import processing.core.PImage;
/**The yellow enemy class.
 * Impliments yellow enemy ai
 * Will always move to the clockwise
 * Direction from the previous direction.
 * */
public class YellowEnemy extends Enemy {
	public YellowEnemy(PApplet parent, int spawnX, int spawnY) {
		x = spawnX;//Spawning coordinate
		y = spawnY;
		xDir = -1;
		yDir = 0;
		animationStart = 0;
		animationOffset = 0;
		//Loading sprite pictures in to the game.
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
				while(collides()) {//If the yellow enemy collides with a wall.
					if(xDir == -1 && yDir == 0) {//If the yellow enemy is coming from left direction.
						xDir = 0;
						yDir = -1;//The yellow enemy will move upward.
						animationStart = 8;//Set the starting frame to up animation start.
					} else if(xDir == 1 && yDir == 0) {//If the yellow enemy is coming from right direction.
						xDir = 0;
						yDir = 1;//The yellow enemy will move downward.
						animationStart = 12;//Set the starting frame to down animation start.
					} else if(xDir == 0 && yDir == -1) {//If the yellow enemy is coming from up direction.
						xDir = 1;//The yellow enemy will move rightward.
						yDir = 0;
						animationStart = 4;//Set the starting frame to right animation start.
					} else if(xDir == 0 && yDir == 1) {//If the yellow enemy is coming from down direction.
						xDir = -1;//The yellow enemy will move leftward.
						yDir = 0;
						animationStart = 0;//Set the starting frame to left animation start.
					}
				}
			}
			x += xDir;
			y += yDir;
		}
		// Player collision
		if(x == Map.bombGuy.x && y == Map.bombGuy.y) {
			Map.bombGuy.life--;//If the bomb guy collides with the yellow enemy, bomb guy loses one life.
			Map.reloadMap(parent, Map.configURL);
		}
	}
}
