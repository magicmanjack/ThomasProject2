package demolition;

import processing.core.PApplet;
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

    public void draw() {
        background(239, 129, 0);
        Map.maps[0].draw(this);
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
