package demolition;

import processing.core.*;
/**The base class of all entities.
 * Two enemies and the player class will extend from this class.
 * Contains the basic parameters like x and y position.
 * An abstract update method and an abstract draw method.
 * */
public abstract class Entity {
	public int x, y;
	
	public abstract void update(PApplet parent);
	
	public abstract void draw(PApplet parent);
}
