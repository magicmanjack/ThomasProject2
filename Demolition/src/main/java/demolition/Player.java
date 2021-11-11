package demolition;

import processing.core.*;


/** The Player class.
 * The Player class extends the Enemy class 
 * because it is a slight variation on the Enemy class.*/
public class Player extends GameCharacter {
	
	/** The number of lives the player has left.*/
	public int life;
	/** Is true if the dies.*/
	public boolean dead;
	
	/**Constructor.
	 * Initializes the main variables of the player. The PApplet parent is used for loading
	 * the player images. The spawnX and spawnY is used to set the players starting position.*/
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
		animate(); //Updates the animation variables.
		if(collides()) {//If the player collides in the current direction.
			//The direction is set to no direction to stop the player from moving.
			xDir = 0;
			yDir = 0;
		}
		//The players position is updated.
		x += xDir;
		y += yDir;
		//The direction is set to no direction to prevent the player continually moving.
		xDir = 0;
		yDir = 0;
		if(Map.maps[Map.currentLevel].mapStrings[y].charAt(x) == 'G') { // If touching the goal.
			Map.currentLevel++; // The current level is incremented to the next level.
			Map.reloadMap(parent, Map.configURL); // The map is reloaded which reloads the enemies.
		}
	}

}
