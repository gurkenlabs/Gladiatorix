package de.litigame;

import java.awt.geom.Rectangle2D;

import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6)
@EntityInfo(width = 16, height = 6)
@MovementInfo(velocity = 70)

public class Player extends Creature {

	private static Player instance = new Player();

	public static Player getInstance() {
		return instance;
	}

	private Player() {
		super("player");
		addController(new KeyboardEntityController<>(this));
	}

	public boolean touches(Rectangle2D rect) {
		return instance.getBoundingBox().intersects(rect);
	}
}
