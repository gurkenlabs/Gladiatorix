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
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.EntityAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.abilities.RangeAttackAbility;
import de.litigame.hp.EnemyHealthBar;
import de.litigame.items.Items;
import de.litigame.items.Weapon;

@AnimationInfo(spritePrefix = "test")
@CollisionInfo(collision = true, collisionBoxWidth = 4, collisionBoxHeight = 4, valign = Valign.MIDDLE)
@EntityInfo(width = 4, height = 4)
@MovementInfo(velocity = 20)

public class Enemy extends Creature implements IFighter {

	private Ability attackAbility;

	public boolean playHitAnimation = false;

	private double strength = 0;

	public int visionRange = 4000;

	public Enemy() {
		this("enemy");
	}

	public Enemy(String spritesheetName) {
		super(spritesheetName);

		onDeath(e -> Game.loop().perform(2000, () -> Game.world().environment().remove(e)));
		setTarget(Player.getInstance());
		putWeapon((Weapon) Items.getItem("sword_stone"));

		final MovementController<Enemy> controller = new EnemyController(this);
		addController(controller);
		Game.loop().attach(controller);

		addEntityRenderListener(e -> new EnemyHealthBar(this).render(e.getGraphics()));
	}

	public Enemy(String spritesheetName, Weapon weapon, double strength, int health, int visionRange) {
		this(spritesheetName);
		putWeapon(weapon);
		this.strength = strength;
		getHitPoints().setMaxBaseValue(health);
		this.visionRange = visionRange;
	}

	@Override
	public boolean canCollideWith(ICollisionEntity other) {
		return !other.hasTag("barrier");
	}

	@Override
	public IEntityAnimationController<Enemy> createAnimationController() {
		IEntityAnimationController<Enemy> controller = new EntityAnimationController<>(this);
		for (String dir : new String[] { "left", "right", "down", "up" }) {
			controller.add(new Animation(getSpritesheetName() + "_hit_" + dir, false, false));
			controller.add(new Animation(getSpritesheetName() + "_walk_" + dir, false, false));
		}
		controller.addRule(e -> !e.isDead(), e -> {
			String image = e.getSpritesheetName() + "_" + (e.playHitAnimation ? "hit_" : "walk_") + e.getFacingDirection().toString();
			if (e.playHitAnimation) e.setVelocity(20);
			e.playHitAnimation = false;
			return image;
		});
		return controller;
	}

	public Ability getAttackAbility() {
		return attackAbility;
	}

	@Override
	public double getStrength() {
		return strength;
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
