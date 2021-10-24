package demolition;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.data.JSONObject;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;
    
    public App() {
    
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);
        Map.loadMaps(this, "config.json");
        // Load images during setup
    }
    
    public void keyPressed() {
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
		}
    }
    
    public void update() {
    	Map.bombGuy.update();
    }

    public void draw() {
    	update();
        background(239, 129, 0);
        Map.maps[Map.currentLevel].draw(this);
        Map.bombGuy.draw(this);
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
