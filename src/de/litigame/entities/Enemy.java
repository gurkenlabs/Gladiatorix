package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.litigame.StaticEnvironmentLoadedListener;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.abilities.RangeAttackAbility;
import de.litigame.items.Items;
import de.litigame.items.Weapon;

@AnimationInfo(spritePrefix = "test")
@CollisionInfo(collision = true, collisionBoxWidth = 4, collisionBoxHeight = 4, valign = Valign.MIDDLE)
@EntityInfo(width = 4, height = 4)
@MovementInfo(velocity = 20)

public class Enemy extends Creature implements IFighter {

	private Ability attackAbility;

	public int visionRange = 40;

	public Enemy() {
		super("enemy");

		setTarget(Player.getInstance());

		putWeapon((Weapon) Items.getItem("sword"));

		new StaticEnvironmentLoadedListener(e -> {
			MovementController<Enemy> controller = new EnemyController(this);
			addController(controller);
			Game.loop().attach(controller);
		});
	}

	public Ability getAttackAbility() {
		return attackAbility;
	}

	@Override
	public double getStrength() {
		return 0;
	}

	public void putWeapon(Weapon weapon) {
		switch (weapon.type) {
		case RANGE:
			attackAbility = new RangeAttackAbility(this);
			break;
		case MELEE:
			attackAbility = new MeleeAttackAbility(this);
			break;
		default:
			break;
		}
		weapon.overrideAbility(attackAbility);
	}
}
