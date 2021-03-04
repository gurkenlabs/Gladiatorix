package de.litigame.entities;

import java.awt.Color;
import java.util.Set;
import java.util.TreeSet;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.graphics.CreatureShadowImageEffect;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.EntityAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.litigame.GameManager;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.abilities.RangeAttackAbility;
import de.litigame.hotbar.Hotbar;
import de.litigame.hp.PlayerHealthBar;
import de.litigame.input.PlayerController;
import de.litigame.items.Armor;
import de.litigame.items.Item;
import de.litigame.items.Shield;
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

	public final Hotbar hotbar = new Hotbar(this);
	private final MeleeAttackAbility melee = new MeleeAttackAbility(this);

	private int money = 0, lvl = 1;
	private boolean playHitAnimation = false;
	private final RangeAttackAbility range = new RangeAttackAbility(this);
	public final Set<Item> storage = new TreeSet<>((i1, i2) -> i1.getName().compareTo(i2.getName()));

	private Player() {
		super("player");
		addController(new PlayerController(this));

		Game.loop().attach(this);
	}

	public void attack() {
		if (hotbar.getSelectedItem() instanceof Weapon) {
			final Weapon weapon = (Weapon) hotbar.getSelectedItem();
			switch (weapon.type) {
			case MELEE:
				weapon.overrideAbility(melee);
				setVelocity(0);
				melee.cast();
				playHitAnimation = true;
				break;
			case RANGE:
				weapon.overrideAbility(range);
				range.cast();
				break;
			}
		}
	}

	public void buy(ShopEntry entry) {
		if (entry.equippable) storage.add(entry.getItem());
		hotbar.addItem(entry.getItem());
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
		IEntityAnimationController<Player> controller = new EntityAnimationController<>(this);
		for (String armor : new String[] { "gold", "leather", "iron" }) for (String weapon : new String[] { "wood", "stone", "iron", "nosword" }) for (String dir : new String[] { "down", "left", "right", "up" }) {
			controller.add(new Animation("player_" + armor + "_" + weapon + "_shield_walk_" + dir, true, false));
			controller.add(new Animation("player_" + armor + "_" + weapon + "_noshield_walk_" + dir, true, false));
			controller.add(new Animation("player_" + armor + "_" + weapon + "_shield_idle_" + dir, true, true));
			controller.add(new Animation("player_" + armor + "_" + weapon + "_noshield_idle_" + dir, true, true));
			if (!weapon.equals("nosword")) {
				controller.add(new Animation("player_" + armor + "_" + weapon + "_shield_hit_" + dir, false, false));
				controller.add(new Animation("player_" + armor + "_" + weapon + "_noshield_hit_" + dir, false, false));
			}
		}

		controller.addRule(p -> !p.isDead(), p -> {
			String image = "player_" + (p.currentArmor == null ? "leather_" : p.currentArmor.getPlayerSkin() + "_") + (p.hotbar.getSelectedItem() instanceof Weapon ? ((Weapon) p.hotbar.getSelectedItem()).playerSkin() + "_" : "nosword_") + (currentShield == null ? "noshield_" : "shield_") + (p.playHitAnimation ? "hit_" : (p.isIdle() ? "idle_" : "walk_"))
					+ p.getFacingDirection().name().toLowerCase();
			if (p.playHitAnimation) p.setVelocity(70);
			p.playHitAnimation = false;
			return image;
		}, 1);
		CreatureShadowImageEffect effect = new CreatureShadowImageEffect(this, new Color(24, 30, 28, 100));
		effect.setOffsetY(1);
		controller.add(effect);
		return controller;
	}

	public void dropItem() {
		hotbar.dropSelectedItem();
	}

	public void equip(Armor armor) {
		if (currentArmor != null) getHitPoints().removeModifier(currentArmor.healthBuff());
		getHitPoints().addModifier(armor.healthBuff());
		currentArmor = armor;
	}

	@Override
	public double getStrength() {
		// TODO Auto-generated method stub
		return 10;
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
	}
}
