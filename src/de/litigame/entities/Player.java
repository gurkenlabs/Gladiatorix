package de.litigame.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.abilities.RangeAttackAbility;
import de.litigame.hotbar.Hotbar;
import de.litigame.input.PlayerController;
import de.litigame.items.Weapon;
import de.litigame.utilities.GeometryUtilities;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6, valign = Valign.MIDDLE)
@EntityInfo(width = 16, height = 6)
@MovementInfo(velocity = 70)

public class Player extends Creature implements IUpdateable, IFighter {

	private static final Player instance = new Player();

	private static final int PICKUP_RANGE = 20;

	public static Player getInstance() {
		return instance;
	}

	public final Hotbar hotbar = new Hotbar(this);
	private final Set<InteractListener> interactListeners = new HashSet<>();
	private final MeleeAttackAbility melee = new MeleeAttackAbility(this);
	private final RangeAttackAbility range = new RangeAttackAbility(this);

	private Player() {
		super("player");

		addController(new PlayerController(this));

		Game.loop().attach(this);
	}

	public void addInteractListener(InteractListener listener) {
		interactListeners.add(listener);
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
				weapon.overrideAbility(range);
				range.cast();
				break;
			}
		}
	}

	public void dropItem() {
		hotbar.dropSelectedItem();
	}

	@Override
	public double getStrength() {
		// TODO Auto-generated method stub
		return 10;
	}

	public void interact() {
		for (InteractListener listener : interactListeners) listener.interact(this);
		pickUpItem();
		Game.world().environment().interact(this);
	}

	public void pickUpItem() {
		List<IEntity> props = new ArrayList<>();
		for (Prop prop : Game.world().environment().getProps()) {
			if (getCenter().distance(prop.getCenter()) <= PICKUP_RANGE) props.add(prop);
		}
		ItemProp nearest = (ItemProp) GeometryUtilities.getNearestEntity(this, props);
		if (nearest != null && hotbar.addItem(nearest.item)) Game.world().environment().remove(nearest);
	}

	public void removeInteractListener(InteractListener listener) {
		interactListeners.remove(listener);
	}

	@Override
	public void update() {
		if (hotbar.getSelectedItem() instanceof Weapon) {
			setTurnOnMove(false);
			setAngle(GeometricUtilities.calcRotationAngleInDegrees(getCenter(), Input.mouse().getMapLocation()));
		} else setTurnOnMove(true);
	}
}
