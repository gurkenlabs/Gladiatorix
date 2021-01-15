package de.litigame.entities;

import java.awt.geom.Rectangle2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Entity;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.hotbar.Hotbar;
import de.litigame.items.Weapon;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6, valign = Valign.MIDDLE)
@EntityInfo(width = 16, height = 6)
@MovementInfo(velocity = 70)

public class Player extends Creature implements IUpdateable, IFighter {

	private static Player instance = new Player();

	public static Player getInstance() {
		return instance;
	}

	public final Hotbar hotbar = new Hotbar();
	public final MeleeAttackAbility melee = new MeleeAttackAbility(this);

	private Player() {
		super("player");

		Game.loop().attach(this);
	}

	public void attack() {
		if (hotbar.getSelectedItem() instanceof Weapon) {
			Weapon weapon = (Weapon) hotbar.getSelectedItem();
			switch (weapon.type) {
			case MELEE:
				weapon.overrideAbility(melee);
				melee.cast();
				break;
			case RANGE:
				break;
			}
		}
	}

	public double distanceTo(Entity other) {
		return getLocation().distance(other.getLocation());
	}

	@Override
	public double getStrength() {
		// TODO Auto-generated method stub
		return 10;
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
