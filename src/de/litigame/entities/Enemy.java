package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.entities.*;
import de.litigame.StaticEnvironmentLoadedListener;
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

	public int visionRange = 40;

	public Enemy() {
		super("enemy");
		setTarget(Player.getInstance());
		putWeapon((Weapon) Items.getItem("sword"));
		EnemyHealthBar hb = new EnemyHealthBar(this);

		StaticEnvironmentLoadedListener.attach(e -> {
			EnemyController controller = new EnemyController(this);
			addController(controller);
			Game.loop().attach(controller);
			//Game.loop().attach(hb);
		});

		addEntityRenderListener(e -> hb.render(e.getGraphics()));
	}

	public Ability getAttackAbility() {
		return attackAbility;
	}

	@Override
	public double getStrength() {
		return 1;
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
