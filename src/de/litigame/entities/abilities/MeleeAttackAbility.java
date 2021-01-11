package de.litigame.entities.abilities;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICombatEntity;

@AbilityInfo(name = "MeleeAttackAbility", cooldown = 400, range = 10, impact = 60, impactAngle = 90, value = 1, duration = 1, multiTarget = true)

public class MeleeAttackAbility extends Ability {

	private static class MeleeAttackEffect extends Effect {

		protected MeleeAttackEffect(Ability ability) {
			super(ability, EffectTarget.FRIENDLY);
		}

		@Override
		protected void apply(ICombatEntity entity) {
			super.apply(entity);
			entity.hit(100);
		}

	}

	public MeleeAttackAbility(Creature executor) {
		super(executor);

		addEffect(new MeleeAttackEffect(this));
	}

}
