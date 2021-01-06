package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@AnimationInfo(spritePrefix = "prop-portal")

public class Portal extends Prop implements IUpdateable {

	public Portal(String spritesheetName) {
		super(spritesheetName);
		Game.loop().attach(this);
	}

	@Override
	public void update() {
		if (Player.getInstance().touches(getBoundingBox())) GameManager.enterPortal(this);
	}

}
