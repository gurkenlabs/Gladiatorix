package de.litigame.weapons;

import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.abilities.AbilityAttributes;
import de.gurkenlabs.litiengine.attributes.Attribute;

public class RangeWeaponAttributes {

	private static final int COUNT = 6; // 0: cooldown, 1: duration, 2: impact, 3: impactAngle, 4:range, 5: value
	final List<Attribute<Integer>> list = new ArrayList<>();

	public RangeWeaponAttributes() {
		for (int i = 0; i < COUNT; ++i) {
			list.add(new Attribute<>(0));
		}
	}

	public void mergeAbilityAttributes(AbilityAttributes other) {
		other.cooldown().setBaseValue(cooldown().getBase());
		other.cooldown().getModifiers().clear();
		other.cooldown().getModifiers().addAll(cooldown().getModifiers());

		other.duration().setBaseValue(duration().getBase());
		other.duration().getModifiers().clear();
		other.duration().getModifiers().addAll(duration().getModifiers());

		other.impact().setBaseValue(impact().getBase());
		other.impact().getModifiers().clear();
		other.impact().getModifiers().addAll(impact().getModifiers());

		other.impactAngle().setBaseValue(impactAngle().getBase());
		other.impactAngle().getModifiers().clear();
		other.impactAngle().getModifiers().addAll(impactAngle().getModifiers());

		other.range().setBaseValue(range().getBase());
		other.range().getModifiers().clear();
		other.range().getModifiers().addAll(range().getModifiers());

		other.value().setBaseValue(value().getBase());
		other.value().getModifiers().clear();
		other.value().getModifiers().addAll(value().getModifiers());
	}
}
