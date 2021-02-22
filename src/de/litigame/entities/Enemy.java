package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.physics.MovementController;
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

	public int visionRange = 4000;

	public Enemy() {
		this("enemy");
	}

	public Enemy(String spritesheetName) {
		super(spritesheetName);

		onDeath(e -> Game.loop().perform(2000, () -> Game.world().environment().remove(e)));

		setTarget(Player.getInstance());

		putWeapon((Weapon) Items.getItem("sword"));

		final MovementController<Enemy> controller = new EnemyController(this);
		addController(controller);
		Game.loop().attach(controller);
	}

	@Override
	public boolean canCollideWith(ICollisionEntity other) {
		return !other.hasTag("barrier");
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
