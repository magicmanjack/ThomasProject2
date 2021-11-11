package demolition;

import processing.core.*;
/**The base class of all entities.
 * Contains the basic parameters like the x and y position.
 * A template of an update and draw method is provided.
 * */
public abstract class Entity {
	/** The position of the entity.*/
	public int x, y;
	
	public abstract void update(PApplet parent);
	
	public abstract void draw(PApplet parent);
}
