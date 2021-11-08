package demolition;

import processing.core.*;
import processing.data.JSONObject;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;
    
    public static boolean keyHeld;
    
    public PFont gameFont;
    
    public PImage playerIcon;
    public PImage clockIcon;
    
    public long deltaFrames;
    
    public App() {
    
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);
        gameFont = createFont("src/main/resources/PressStart2P-Regular.ttf", 16);
        playerIcon = loadImage("src/main/resources/icons/player.png");
        clockIcon = loadImage("src/main/resources/icons/clock.png");
        Map.loadMaps(this, "config.json");
        // Load images during setup
    }
    
    public void keyPressed() {
    	if(!keyHeld) {
    		keyHeld = true;
	    	if(key == CODED) {
				switch(keyCode) {
				case PConstants.LEFT:
					Map.bombGuy.animationStart = 0;
					Map.bombGuy.xDir = -1;
					Map.bombGuy.yDir = 0;
					break;
				case PConstants.RIGHT:
					Map.bombGuy.animationStart = 4;
					Map.bombGuy.xDir = 1;
					Map.bombGuy.yDir = 0;
					break;
				case PConstants.UP:
					Map.bombGuy.animationStart = 8;
					Map.bombGuy.xDir = 0;
					Map.bombGuy.yDir = -1;
					break;
				case PConstants.DOWN:
					Map.bombGuy.animationStart = 12;
					Map.bombGuy.xDir = 0;
					Map.bombGuy.yDir = 1;
					break;
				}
			} else {
				if(key == ' ') {
					Bomb.bombs.add(new Bomb(this, Map.bombGuy.x, Map.bombGuy.y));
				}
			}
    	}
    }
    
    public void keyReleased() {
    	keyHeld = false;
    }
    
    public void update() {
    	deltaFrames++;
    	if(Map.bombGuy.life > 0 && Map.currentLevel < Map.maps.length) {
    		if(deltaFrames % 60 == 0) {
    			Map.maps[Map.currentLevel].time--;
    		}
	    	Map.bombGuy.update(this);
	    	//Enemies
	    	for(int i = 0; i < Map.enemies.size(); i++) {
	    		Map.enemies.get(i).update(this);
	    	}
	    	for(int i = 0; i < Bomb.bombs.size(); i++) {
	    		Bomb.bombs.get(i).update(this);
	    	}
    	}
    }

    public void draw() {
    	update();
        background(239, 129, 0);
        if(Map.bombGuy.life > 0 && Map.currentLevel < Map.maps.length && Map.maps[Map.currentLevel].time >= 0) {
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
        } else if(Map.currentLevel >= Map.maps.length) {
        	//text time
        	fill(color(0, 0, 0));
        	textFont(gameFont);
        	textAlign(CENTER);
        	text("YOU WIN", 240, 200);
        } else if(Map.bombGuy.life <= 0 || Map.maps[Map.currentLevel].time < 0) {
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
