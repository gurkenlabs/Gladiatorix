package de.litigame.entities;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@AnimationInfo(spritePrefix = "prop-portal")

public class Portal extends Prop implements IUpdateable {

	public Portal(String spritesheetName) {
		super(spritesheetName);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
