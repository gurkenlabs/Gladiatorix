package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@AnimationInfo(spritePrefix = "prop-portal")

public class Portal extends Prop {

	public Portal(String spritesheetName) {
		super(spritesheetName);
	}
}
