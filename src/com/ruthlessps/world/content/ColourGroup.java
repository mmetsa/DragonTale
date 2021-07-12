package com.ruthlessps.world.content;

/**
 * Represents a colour group
 * 
 * @author 2012
 *
 */
public class ColourGroup {

	/**
	 * The main set
	 */
	public ColourSet set;

	/**
	 * The first preset
	 */
	public ColourSet[] presets;

	public ColourGroup() {
		set = new ColourSet();
		presets = new ColourSet[3];
		presets[0] = new ColourSet();
		presets[1] = new ColourSet();
		presets[2] = new ColourSet();
	}
}
