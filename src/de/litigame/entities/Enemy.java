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
import de.litigame.spawning.Spawnpoints;

@AnimationInfo(spritePrefix = "test")
@CollisionInfo(collision = true, collisionBoxWidth = 4, collisionBoxHeight = 4, valign = Valign.MIDDLE)
@EntityInfo(width = 4, height = 4)
@MovementInfo(velocity = 20)

public class Enemy extends Creature implements IFighter {

	private Ability attackAbility;

	public boolean playHitAnimation = false;

	private double strength = 0;

	private Weapon weapon;

	public int visionRange = 4000, moneyLoot;

	public Enemy() {
		this("enemy");
	}

	public Enemy(Enemy other) {
		this(other.getSpritesheetName(), null, other.strength, other.getHitPoints().getMax(), other.visionRange, other.moneyLoot);
		putWeapon(other.weapon);
	}

	public Enemy(String spritesheetName) {
		super(spritesheetName);

		onDeath(e -> {
			Game.loop().perform(2000, () -> Game.world().environment().remove(e));
			if (Spawnpoints.allDead() && !Spawnpoints.isOver()) {
				Spawnpoints.spawnNextWave();
			}
			Player.getInstance().changeMoney(getmoneyLoot());
		});
		setTarget(Player.getInstance());
		putWeapon((Weapon) Items.getItem("Trainingsschwert"));

		final MovementController<Enemy> controller = new EnemyController(this);
		addController(controller);

		addEntityRenderListener(e -> new EnemyHealthBar(this).render(e.getGraphics()));
	}

	public Enemy(String spritesheetName, Weapon weapon, double strength, int health, int visionRange, int moneyLoot) {
		this(spritesheetName);
		putWeapon(weapon);
		this.strength = strength;
		getHitPoints().setMaxBaseValue(health);
		getHitPoints().setBaseValue(health);
		this.visionRange = visionRange;
		this.moneyLoot = moneyLoot;
	}

	@Override
	public boolean canCollideWith(ICollisionEntity other) {
		return !other.hasTag("barrier");
	}

	@Override
	public IEntityAnimationController<Enemy> createAnimationController() {
		final IEntityAnimationController<Enemy> controller = new EntityAnimationController<>(this);
		for (final String dir : new String[] { "left", "right", "down", "up" }) {
			controller.add(new Animation(getSpritesheetName() + "_hit_" + dir, true, false));
			controller.add(new Animation(getSpritesheetName() + "_walk_" + dir, true, false));
		}
		controller.addRule(e -> !e.isDead(), e -> {
			final String image = e.getSpritesheetName() + "_" + (e.playHitAnimation ? "hit_" : "walk_") + e.getFacingDirection().toString().toLowerCase();
			if (e.playHitAnimation) {
				e.setVelocity(20);
			}
			e.playHitAnimation = false;
			return image;
		});
		return controller;
	}

	public Ability getAttackAbility() {
		return attackAbility;
	}

	public int getmoneyLoot() {
		return moneyLoot;
	}

	@Override
	public double getStrength() {
		return strength;
	}

	public void putWeapon(Weapon weapon) {
		if (weapon == null) {
			return;
		}
		this.weapon = weapon;
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
