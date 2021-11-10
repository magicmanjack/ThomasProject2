package demolition;

import processing.core.*;
import processing.data.JSONObject;

/**The main class of the game.
 * This class contains the main method. This class
 * also contains the setup method which is called when the
 * game is started. This class also contains the main update and draw
 * methods. */
public class App extends PApplet {
	
	/**The pixel width of the game window.*/
    public static final int WIDTH = 480;
    /**The pixel height of the game window.*/
    public static final int HEIGHT = 480;
    /**The frames per second of the game.*/
    public static final int FPS = 60;
    
    /**The state of a key being held*
     * If a key is held down, keyHeld is set to true.*/
    public static boolean keyHeld;
    
    /**The UI font*
     * This is the font of the game over and winning screens.*/
    public PFont gameFont;
    
    /**The UI image of the player*/
    public PImage playerIcon;
    /**The UI image of the clock*/
    public PImage clockIcon;
    /**Elapsed frames.
     * This is the elapsed frames since the game was started.
     */
    public long deltaFrames;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS); // Sets the frame rate.
        gameFont = createFont("src/main/resources/PressStart2P-Regular.ttf", 16);
        playerIcon = loadImage("src/main/resources/icons/player.png");
        clockIcon = loadImage("src/main/resources/icons/clock.png");
        //The font and UI images are loaded.
        Map.loadMaps(this, "config.json");
        //The maps are loaded from the config file.
    }
    
    public void keyPressed() {
    	if(!keyHeld) { //If the key is not being held.
    		keyHeld = true;
	    	if(key == CODED) {
				switch(keyCode) {
				case PConstants.LEFT:
					Map.bombGuy.animationStart = 0;
					Map.bombGuy.xDir = -1;
					Map.bombGuy.yDir = 0;
					// The bomb guy movement and animations are set to the left.
					break;
				case PConstants.RIGHT:
					Map.bombGuy.animationStart = 4;
					Map.bombGuy.xDir = 1;
					Map.bombGuy.yDir = 0;
					// The bomb guy movement and animations are set to the right.
					break;
				case PConstants.UP:
					Map.bombGuy.animationStart = 8;
					Map.bombGuy.xDir = 0;
					Map.bombGuy.yDir = -1;
					// The bomb guy movement and animations are set to up.
					break;
				case PConstants.DOWN:
					Map.bombGuy.animationStart = 12;
					Map.bombGuy.xDir = 0;
					Map.bombGuy.yDir = 1;
					// The bomb guy movement and animations are set to down.
					break;
				}
			} else {
				if(key == ' ') { // If the key is not coded (space is not coded) and the space bar is pressed.
					Bomb.bombs.add(new Bomb(this, Map.bombGuy.x, Map.bombGuy.y));
					// A new bomb is added with the same x and y as the bomb guy.
				}
			}
    	}
    }
    
    public void keyReleased() {
    	keyHeld = false;
    	// keyHeld is only reset when all keys are released.
    }
    
    /** Updates the game state.
     * Updates all the game entities and the map before drawing them.*/
    public void update() {
    	deltaFrames++;
    	if(Map.bombGuy.life > 0 && Map.currentLevel < Map.maps.length) { // If the player still has lives and the current level has not exceeded the amount of maps.
    		if(deltaFrames % 60 == 0) { // If a second has elapsed. (There are 60 frames per second)
    			Map.maps[Map.currentLevel].time--; // The timer decreases by a second.
    		}
	    	Map.bombGuy.update(this); // The player is updated.
	    	//Enemies
	    	for(int i = 0; i < Map.enemies.size(); i++) {
	    		Map.enemies.get(i).update(this); // Each enemy in the enemies ArrayList is updated.
	    	}
	    	for(int i = 0; i < Bomb.bombs.size(); i++) {
	    		Bomb.bombs.get(i).update(this); // Each bomb in the bomb ArrayList is updated.
	    	}
    	}
    }
    
    public void draw() {
    	update(); // The game is updated before drawn.
        background(239, 129, 0); // The background is set to the shade of orange.
        if(Map.bombGuy.life > 0 && Map.currentLevel < Map.maps.length && Map.maps[Map.currentLevel].time >= 0) { /* If the bomb guy has lives left and the current level
        has not exceeded the number of maps loaded and there is still time left, the game entities and map are all drawn. */
        	// UI
        	image(playerIcon, 128, 16, 32, 32);
        	fill(color(0, 0, 0));
        	textFont(gameFont);
        	textAlign(LEFT, TOP);
        	text(Map.bombGuy.life, 168, 26);
        	image(clockIcon, 256, 16, 32, 32);
        	text(Map.maps[Map.currentLevel].time, 296, 26);
        	// Map
	        Map.maps[Map.currentLevel].draw(this);
	        //Bombs
	        for(int i = 0; i < Bomb.bombs.size(); i++) {
	    		Bomb.bombs.get(i).draw(this);
	    	}
	        //Player
	        Map.bombGuy.draw(this);
	        //Enemies
	        for(int i = 0; i < Map.enemies.size(); i++) {
	    		Map.enemies.get(i).draw(this);
	    	}
        } else if(Map.currentLevel >= Map.maps.length) { /* If the current level is greater then the number of maps 
        (meaning the player has reached the end of the game), the winning screen is shown.*/
        	//text time
        	fill(color(0, 0, 0));
        	textFont(gameFont);
        	textAlign(CENTER);
        	text("YOU WIN", 240, 200);
        } else if(Map.bombGuy.life <= 0 || Map.maps[Map.currentLevel].time < 0) { /* If the bomb guy runs out of lives, 
        then the losing screen is shown.*/
        	//text time
        	fill(color(0, 0, 0));
        	textFont(gameFont);
        	textAlign(CENTER);
        	text("GAME OVER", 240, 200);
        }
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
