package de.litigame.weapons;

import de.gurkenlabs.litiengine.abilities.AbilityAttributes;
import de.gurkenlabs.litiengine.attributes.Attribute;

public class WeaponAttributes {

	protected Attribute<Integer> cooldown;
	protected Attribute<Integer> duration;
	protected Attribute<Integer> impact;
	protected Attribute<Integer> impactAngle;
	protected Attribute<Integer> range;
	protected Attribute<Integer> value;

	public Attribute<Integer> cooldown() {
		return cooldown;
	}

	public Attribute<Integer> duration() {
		return duration;
	}

	public Attribute<Integer> impact() {
		return impact;
	}

	public Attribute<Integer> impactAngle() {
		return impactAngle;
	}

	public void mergeAbilityAttributes(AbilityAttributes other) {
		other.cooldown().setBaseValue(cooldown.getBase());
		other.cooldown().getModifiers().clear();
		other.cooldown().getModifiers().addAll(cooldown.getModifiers());

		other.duration().setBaseValue(duration.getBase());
		other.duration().getModifiers().clear();
		other.duration().getModifiers().addAll(duration.getModifiers());

		other.impact().setBaseValue(impact.getBase());
		other.impact().getModifiers().clear();
		other.impact().getModifiers().addAll(impact.getModifiers());

		other.impactAngle().setBaseValue(impactAngle.getBase());
		other.impactAngle().getModifiers().clear();
		other.impactAngle().getModifiers().addAll(impactAngle.getModifiers());

		other.range().setBaseValue(range.getBase());
		other.range().getModifiers().clear();
		other.range().getModifiers().addAll(range.getModifiers());

		other.value().setBaseValue(value.getBase());
		other.value().getModifiers().clear();
		other.value().getModifiers().addAll(value.getModifiers());
	}

	public Attribute<Integer> range() {
		return range;
	}

	public Attribute<Integer> value() {
		return value;
	}
}
