package de.litigame.weapons;

import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.abilities.AbilityAttributes;
import de.gurkenlabs.litiengine.attributes.Attribute;

public class WeaponAttributes {

	private static final int COUNT = 6; // 0: cooldown, 1: duration, 2: impact, 3: impactAngle, 4:range, 5: value
	final List<Attribute<Integer>> list = new ArrayList<>();

	public WeaponAttributes() {
		for (int i = 0; i < COUNT; ++i) {
			list.add(new Attribute<>(0));
		}
	}

	public Attribute<Integer> cooldown() {
		return list.get(0);
	}

	public Attribute<Integer> duration() {
		return list.get(1);
	}

	public Attribute<Integer> impact() {
		return list.get(2);
	}

	public Attribute<Integer> impactAngle() {
		return list.get(3);
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

	public Attribute<Integer> range() {
		return list.get(4);
	}

	public Attribute<Integer> value() {
		return list.get(5);
	}
}
