package demolition;

import processing.core.PApplet;

public class Map {
	
	public Map(PApplet parent, String filename) {
		String[] mapStrings = parent.loadStrings(filename);
		for(int i = 0; i < mapStrings.length; i++) {
			System.out.println(mapStrings[i]);
		}
	}

}
