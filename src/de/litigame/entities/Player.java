package de.litigame.entities;

import java.awt.geom.Rectangle2D;
import java.util.*;

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
import de.litigame.input.IInteractListener;
import de.litigame.input.PlayerController;
import de.litigame.items.Weapon;
import de.litigame.utilities.GeometryUtilities;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6, valign = Valign.MIDDLE)
@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)

public class Player extends Creature implements IUpdateable, IFighter {

	private static final Player instance = new Player();

	private static final int PICKUP_RANGE = 20;

	public static Player getInstance() {
		return instance;
	}

	public final Hotbar hotbar = new Hotbar(this);
	public ArrayList<NpcController> interactListener;
	private final MeleeAttackAbility melee = new MeleeAttackAbility(this);
	private final RangeAttackAbility range = new RangeAttackAbility(this);

	private Player() {
		super("player");
		interactListener = new ArrayList<>();
		addController(new PlayerController(this));

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
		interactNPC();
		pickUpItem();
		Game.world().environment().interact(this);
	}

	public void interactNPC() {
		//System.out.println(interactListener.size());
		for(NpcController npc : interactListener){
			npc.getEntity().interact();
		}
	}
	public void pickUpItem() {
		List<IEntity> props = new ArrayList<>();
		for (Prop prop : Game.world().environment().getProps()) {
			if (getCenter().distance(prop.getCenter()) <= PICKUP_RANGE) props.add(prop);
		}
		ItemProp nearest = (ItemProp) GeometryUtilities.getNearestEntity(this, props);
		if (nearest != null && hotbar.addItem(nearest.item)) Game.world().environment().remove(nearest);
	}

	public boolean touches(Rectangle2D rect) {
		return instance.getBoundingBox().intersects(rect);
	}

	@Override
	public void update() {
		if (hotbar.getSelectedItem() instanceof Weapon) {
			setTurnOnMove(false);
			setAngle(GeometricUtilities.calcRotationAngleInDegrees(getCenter(), Input.mouse().getMapLocation()));
		} else setTurnOnMove(true);
	}
}
