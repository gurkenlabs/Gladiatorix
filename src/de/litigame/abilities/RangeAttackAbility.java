package de.litigame.abilities;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityExecution;
import de.gurkenlabs.litiengine.entities.Creature;
import de.litigame.entities.IFighter;
import de.litigame.entities.Projectile;

public class RangeAttackAbility extends Ability {

	public RangeAttackAbility(IFighter executor) {
		super((Creature) executor);
	}

	@Override
	public AbilityExecution cast() {
		if (canCast()) {
			Projectile lol = new Projectile(getExecutor(), this);
			lol.addHitListener((e, t, p) -> {
				t.hit((int) p.getStrength());
			});
			lol.addFallListener((e, f) -> System.out.println("aww man"));
		}
		return super.cast();
	}
}
