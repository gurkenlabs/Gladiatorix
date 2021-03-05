package de.litigame.entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Set;
import java.util.TreeSet;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.attributes.RangeAttribute;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.graphics.CreatureShadowImageEffect;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.EntityAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.abilities.RangeAttackAbility;
import de.litigame.hotbar.Hotbar;
import de.litigame.hp.PlayerHealthBar;
import de.litigame.input.PlayerController;
import de.litigame.items.Armor;
import de.litigame.items.Item;
import de.litigame.items.Items;
import de.litigame.items.Shield;
import de.litigame.items.Potion;
import de.litigame.items.Weapon;
import de.litigame.shop.ShopEntry;
import de.litigame.utilities.GeometryUtilities;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6, valign = Valign.MIDDLE)
@EntityInfo(width = 16, height = 6)
@MovementInfo(velocity = 70)

public class Player extends Creature implements IUpdateable, IFighter {

	private static final Player instance = new Player();

	private static final double INTERACT_RANGE = 20;

	public static Player getInstance() {
		return instance;
	}

	private Armor currentArmor;

	private Shield currentShield;

	public final PlayerHealthBar healthBar = new PlayerHealthBar(this);

	public int healthLastInstance = 100;
	public final Hotbar hotbar = new Hotbar(this);

	private int AttackCooldown;

	private final MeleeAttackAbility melee = new MeleeAttackAbility(this);
	private int money = 0, lvl = 1;
	private boolean playHitAnimation = false;
	private final RangeAttackAbility range = new RangeAttackAbility(this);

	public final Set<Item> storage = new TreeSet<>((i1, i2) -> i1.getName().compareTo(i2.getName()));
	public final RangeAttribute<Integer> strength = new RangeAttribute<>(100, 0, 10);

	public int updatetimer = 0;

	private Player() {
		super("player");
		addController(new PlayerController(this));
		Game.loop().attach(this);
	}

	public void attack() {
		if (hotbar.getSelectedItem() instanceof Weapon && AttackCooldown == 0) {
			final Weapon weapon = (Weapon) hotbar.getSelectedItem();
			switch (weapon.type) {
			case MELEE:
				weapon.overrideAbility(melee);
				setVelocity(0);
				melee.cast();
				playHitAnimation = true;
				Game.audio().playSound(Resources.sounds().get("swoosh"));
				AttackCooldown = 30;
				break;
			case RANGE:
				weapon.overrideAbility(range);
				range.cast();
				break;

			}
		} else if (hotbar.getSelectedItem() instanceof Potion) {
			((Potion) hotbar.getSelectedItem()).consume(this);
		}
	}

	public void buy(ShopEntry entry) {
		if (entry.equippable) {
			storage.add(entry.getItem());
		}
		if (entry.getItem() instanceof Armor) {
			equip((Armor) entry.getItem());
		} else if (entry.getItem() instanceof Shield) {
			equip((Shield) entry.getItem());
		} else {
			hotbar.addItem(entry.getItem());
		}
	}

	public boolean canBuy(ShopEntry entry) {
		return lvl >= entry.requiredLevel && money >= entry.price;
	}

	public void changeLvl(int shift) {
		lvl += shift;
	}

	public void changeMoney(int shift) {
		money += shift;
	}

	@Override
	public IEntityAnimationController<Player> createAnimationController() {
		final IEntityAnimationController<Player> controller = new EntityAnimationController<>(this);
		for (final String armor : new String[] { "gold", "leather", "iron" }) {
			for (final String weapon : new String[] { "wood", "stone", "iron", "nosword" }) {
				for (final String dir : new String[] { "down", "left", "right", "up" }) {
					controller
							.add(new Animation("player_" + armor + "_" + weapon + "_shield_walk_" + dir, true, false));
					controller.add(
							new Animation("player_" + armor + "_" + weapon + "_noshield_walk_" + dir, true, false));
					controller.add(new Animation("player_" + armor + "_" + weapon + "_shield_idle_" + dir, true, true));
					controller
							.add(new Animation("player_" + armor + "_" + weapon + "_noshield_idle_" + dir, true, true));
					if (!weapon.equals("nosword")) {
						controller.add(
								new Animation("player_" + armor + "_" + weapon + "_shield_hit_" + dir, false, false));
						controller.add(
								new Animation("player_" + armor + "_" + weapon + "_noshield_hit_" + dir, false, false));
					}
				}
			}
		}

		controller.addRule(p -> !p.isDead(), p -> {
			final String image = "player_"
					+ (p.currentArmor == null ? "leather_" : p.currentArmor.getPlayerSkin() + "_")
					+ (p.hotbar.getSelectedItem() instanceof Weapon
							? ((Weapon) p.hotbar.getSelectedItem()).playerSkin() + "_"
							: "nosword_")
					+ (currentShield == null ? "noshield_" : "shield_")
					+ (p.playHitAnimation ? "hit_" : (p.isIdle() ? "idle_" : "walk_"))
					+ p.getFacingDirection().name().toLowerCase();
			if (p.playHitAnimation) {
				p.setVelocity(70);
			}
			p.playHitAnimation = false;
			return image;
		}, 1);
		final CreatureShadowImageEffect effect = new CreatureShadowImageEffect(this, new Color(24, 30, 28, 100));
		effect.setOffsetY(1);
		controller.add(effect);
		return controller;
	}

	public void dropItem() {
		hotbar.dropSelectedItem();
	}

	public void equip(Armor armor) {
		if (currentArmor != null) {
			getHitPoints().removeModifier(currentArmor.healthBuff());
		}
		getHitPoints().addModifier(armor.healthBuff());
		currentArmor = armor;
	}

	public void equip(Shield shield) {
		if (currentShield != null) {
			getHitPoints().removeModifier(currentShield.healthBuff());
		}
		getHitPoints().addModifier(shield.healthBuff());
		currentShield = shield;
	}

	public int getLvl() {
		return lvl;
	}

	public int getMoney() {
		return money;
	}

	@Override
	public double getStrength() {
		// TODO Auto-generated method stub
		return 10;
	}

	public void init(String[] hotbar, int m, int l, Point2D loc, int hp, int selectedSlot) {
		money = m;
		lvl = l;
		this.setLocation(loc);
		this.hotbar.setToSlot(selectedSlot);
		for (int i = 0; i < hotbar.length; i++) {
			if (!hotbar[i].equals("null")) {
				this.hotbar.replace(Items.getItem(hotbar[i]), i);
			} else {
				this.hotbar.replace(null, i);
			}
		}
		if (getHitPoints().get() == getHitPoints().getMax()) {
			this.hit(getHitPoints().getMax() - hp);
		}
	}

	public void interact() {
		final IInteractEntity nearest = GeometryUtilities.getNearestEntity(this, GameManager.interactEntities);

		if (nearest != null && getCenter().distance(nearest.getCenter()) <= INTERACT_RANGE) {
			nearest.interact(this);
		} else {
			Game.world().environment().interact(this);
		}
	}

	@Override
	public void update() {

		// Check if Player is moving, if yes play walk sound
		if (!isIdle() && Game.screens().current().getName() == "ingame") {
			if (updatetimer == 0) {
				Game.audio().playSound(Resources.sounds().get("step"));
				updatetimer = 20;
			}
			updatetimer--;
		}
		if (isIdle() && updatetimer != 0) {
			updatetimer = 0;
		}

		if (AttackCooldown > 0) {
			AttackCooldown--;
		}

		// Check if player was hit, if yes play hurt sound
		if (getHitPoints().get() < healthLastInstance) {
			Game.audio().playSound(Resources.sounds().get("grunt"));
		}
		healthLastInstance = getHitPoints().get();
	}
}
