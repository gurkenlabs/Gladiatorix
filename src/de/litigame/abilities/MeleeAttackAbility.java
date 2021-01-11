package de.litigame.abilities;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICombatEntity;

public class MeleeAttackAbility extends Ability {

	private static class MeleeAttackEffect extends Effect {

		protected MeleeAttackEffect(Ability ability) {
			super(ability, EffectTarget.FRIENDLY);
		}

		@Override
		protected void apply(ICombatEntity entity) {
			super.apply(entity);
			entity.hit(getAbility().getAttributes().value().get());
		}
	}

	public MeleeAttackAbility(Creature executor) {
		super(executor);

		addEffect(new MeleeAttackEffect(this));
	}

}
