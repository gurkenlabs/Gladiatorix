package de.litigame.abilities;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.litigame.entities.IFighter;

@AbilityInfo(name = "MeleeAttackAbility")

public class MeleeAttackAbility extends Ability implements IHitAbility {

	private static class MeleeAttackEffect extends Effect {

		protected MeleeAttackEffect(Ability ability) {
			super(ability, EffectTarget.FRIENDLY);
		}

		@Override
		protected void apply(ICombatEntity entity) {
			super.apply(entity);
			entity.hit((int) ((IFighter) getAbility().getExecutor()).getStrength()
					* getAbility().getAttributes().value().get());
		}
	}

	public MeleeAttackAbility(IFighter executor) {
		super((Creature) executor);

		addEffect(new MeleeAttackEffect(this));
	}

	@Override
	public boolean canHit(ICollisionEntity target) {
		return calculateImpactArea().intersects(target.getCollisionBox());
	}
}
