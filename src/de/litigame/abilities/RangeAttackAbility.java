package de.litigame.abilities;

import java.awt.geom.Line2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityExecution;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.physics.Collision;
import de.litigame.entities.IFighter;
import de.litigame.entities.Projectile;

public class RangeAttackAbility extends Ability implements IHitAbility {

	public RangeAttackAbility(IFighter executor) {
		super((Creature) executor);
	}

	@Override
	public boolean canHit(ICollisionEntity target) {
		Line2D line = new Line2D.Double(getExecutor().getCenter(), target.getCenter());
		if (line.getP1().distance(line.getP2()) > getAttributes().range().get()) return false;
		for (ICollisionEntity collisionEntity : Game.physics().getCollisionEntities(Collision.STATIC)) {
			if (collisionEntity != getExecutor() && collisionEntity != target
					&& !collisionEntity.getClass().equals(Projectile.class)
					&& line.intersects(collisionEntity.getCollisionBox()))
				return false;
		}
		return true;
	}

	@Override
	public AbilityExecution cast() {
		if (canCast()) {
			Projectile lol = new Projectile(getExecutor(), this);
			lol.addHitListener((e, t, p) -> {
				t.hit((int) p.getStrength());
				System.out.println("hit");
			});
			lol.addFallListener((e, f) -> System.out.println("aww man"));
		}
		return super.cast();
	}
}
