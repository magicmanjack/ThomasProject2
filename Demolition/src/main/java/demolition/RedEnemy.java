package demolition;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
/**The red enemy class.
 * Implements red enemy AI.
 * The red enemy will always move to a random
 * direction after colliding with the wall.
 * */
public class RedEnemy extends Enemy {
	/**Constructor.
	 * Initializes all the main variables. The PApplet parent argument is used to load the
	 * Red Enemy's images. The spawnX and spawnY argument is used to set the starting
	 * x and y position.*/
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
	public void update(PApplet parent) {
		animate();
		if(deltaFrames % 60 == 0) { // True every second.
			if(collides()) { // If the red enemy collides with a wall.
				String freeSpaces = "";
				//Check up
				xDir = 0;
				yDir = -1;
				if(!collides()) {
					// Adds up to possible free spaces.
					freeSpaces = freeSpaces + "u";
				}
				
				//Check down
				xDir = 0;
				yDir = 1;
				if(!collides()) {
					// Adds down to possible free spaces.
					freeSpaces = freeSpaces + "d";
				}
				
				//Check left
				xDir = -1;
				yDir = 0;
				if(!collides()) {
					// Adds left to possible free spaces.
					freeSpaces = freeSpaces + "l";
				}
				
				//Check right
				xDir = 1;
				yDir = 0;
				if(!collides()) {
					// Adds right to possible free spaces.
					freeSpaces = freeSpaces + "r";
				}
				
				Random rand = new Random();
				switch((char)freeSpaces.charAt(rand.nextInt(freeSpaces.length()))) { // Chooses a random direction from the possible free spaces string.
					case 'u':
						// Sets the direction to up.
						xDir = 0;
						yDir = -1;
						animationStart = 8;// Sets the starting frame to up.
						break;
					case 'd':
						// Sets the direction to down.
						xDir = 0;
						yDir = 1;
						animationStart = 12;// Sets the starting frame to down.
						break;
					case 'l':
						// Sets the direction to left.
						xDir = -1;
						yDir = 0;
						animationStart = 0;// Sets the starting frame to left.
						break;
					case 'r':
						// Sets the direction to right.
						xDir = 1;
						yDir = 0;
						animationStart = 4;// Sets the starting frame to right.
						break;
				}
			}
			// The x and y position is moved in the decided direction.
			x += xDir;
			y += yDir;
		}
		// Player collision
		if(x == Map.bombGuy.x && y == Map.bombGuy.y) {
			Map.bombGuy.life--;// If the bomb guy collides with the red enemy, bomb guy loses one life.
			Map.reloadMap(parent, Map.configURL);// The map is reloaded so that all walls and enemies are reset.
		}
	}
}
