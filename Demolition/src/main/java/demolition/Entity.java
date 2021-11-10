package demolition;

import processing.core.*;

public abstract class Entity {
	public int x, y;
	
	public abstract void update(PApplet parent);
	
	public abstract void draw(PApplet parent);
}
