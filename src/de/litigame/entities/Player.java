package de.litigame.entities;

import java.awt.geom.Rectangle2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Entity;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.litigame.hotbar.Hotbar;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6)
@EntityInfo(width = 16, height = 6)
@MovementInfo(velocity = 70)

public class Player extends Creature implements IUpdateable {

	private static Player instance = new Player();

	public static Player getInstance() {
		return instance;
	}

	public final Hotbar hotbar;

	private Player() {
		super("player");
		hotbar = new Hotbar();
		Game.loop().attach(this);
	}

	public double distanceTo(Entity other) {
		return getLocation().distance(other.getLocation());
	}

	public void interact() {
		Game.world().environment().interact(this);
	}

	public boolean touches(Rectangle2D rect) {
		return instance.getBoundingBox().intersects(rect);
	}

	@Override
	public void update() {
	}
}
