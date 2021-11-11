package demolition;

import processing.core.PApplet;
import processing.core.PImage;
/**The yellow enemy class.
 * Implements yellow enemy AI.
 * The yellow enemy will always move to the clockwise
 * direction from the previous direction.
 * */
public class YellowEnemy extends Enemy {
	
	/**Constructor.
	 * Initializes all the main variables. The PApplet parent argument is used to load the
	 * Yellow Enemy's images. The spawnX and spawnY argument is used to set the starting
	 * x and y position.*/
	public YellowEnemy(PApplet parent, int spawnX, int spawnY) {
		x = spawnX;
		y = spawnY;
		xDir = -1;
		yDir = 0;
		animationStart = 0;
		animationOffset = 0;
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
				while(collides()) {// If the yellow enemy collides with a wall.
					if(xDir == -1 && yDir == 0) {// If the yellow enemy is moving left
						// The yellow enemy will move upward.
						xDir = 0;
						yDir = -1;
						animationStart = 8;//Sets the starting frame to up.
					} else if(xDir == 1 && yDir == 0) {//If the yellow enemy is moving right.
						// The yellow enemy will now move down.
						xDir = 0;
						yDir = 1;
						animationStart = 12;// Sets the starting frame to down.
					} else if(xDir == 0 && yDir == -1) {// If the yellow enemy is moving up.
						// The yellow enemy will now move right.
						xDir = 1;
						yDir = 0;
						animationStart = 4;// Sets the starting frame to right.
					} else if(xDir == 0 && yDir == 1) {// If the yellow enemy is moving down.
						// The new direction is left.
						xDir = -1;
						yDir = 0;
						animationStart = 0;// Sets the starting frame to left.
					}
				}
			}
			// The x and y position is moved in the decided direction.
			x += xDir;
			y += yDir;
		}
		// Player collision
		if(x == Map.bombGuy.x && y == Map.bombGuy.y) {
			Map.bombGuy.life--;//If the bomb guy collides with the yellow enemy, bomb guy loses one life.
			Map.reloadMap(parent, Map.configURL); // The map is reloaded so that all walls and enemies are reset.
		}
	}
}
