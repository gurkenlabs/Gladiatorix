package de.litigame.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityAttributes;
import de.gurkenlabs.litiengine.attributes.Attribute;

public class Weapon extends Item {

	public enum Type {
		MELEE, RANGE;
	}

	private static final String[] attributeKeys = { "weapon_cooldown", "weapon_duration", "weapon_impact",
			"weapon_impactAngle", "weapon_range", "weapon_value" };
	private final List<Attribute<Integer>> attributes = new ArrayList<>();
	private boolean multiTarget = true;
	private final String playerSkin;
	public final Type type;

	public Weapon(Map<String, String> itemInfo) {
		super(itemInfo);

		for (String attributeKey : attributeKeys) {
			attributes.add(new Attribute<>(0));
		}

		for (int i = 0; i < attributeKeys.length; ++i) {
			String key = attributeKeys[i];
			if (itemInfo.containsKey(key)) attributes.get(i).setBaseValue(Integer.valueOf(itemInfo.get(key)));
		}

		if (itemInfo.containsKey("weapon_multiTarget"))
			multiTarget = Boolean.valueOf(itemInfo.get("weapon_multiTarget"));

		playerSkin = itemInfo.get("weapon_skin");

		switch (itemInfo.get("weapon_type")) {
		case "melee":
			type = Type.MELEE;
			break;
		case "range":
			type = Type.RANGE;
			break;
		default:
			type = null;
		}
	}

	public Attribute<Integer> cooldown() {
		return attributes.get(0);
	}

	public Attribute<Integer> duration() {
		return attributes.get(1);
	}

	public Attribute<Integer> impact() {
		return attributes.get(2);
	}

	public Attribute<Integer> impactAngle() {
		return attributes.get(3);
	}

	public void overrideAbility(Ability ability) {
		AbilityAttributes attributes = ability.getAttributes();

		attributes.cooldown().setBaseValue(cooldown().getBase());
		attributes.cooldown().getModifiers().clear();
		attributes.cooldown().getModifiers().addAll(cooldown().getModifiers());

		attributes.duration().setBaseValue(duration().getBase());
		attributes.duration().getModifiers().clear();
		attributes.duration().getModifiers().addAll(duration().getModifiers());

		attributes.impact().setBaseValue(impact().getBase());
		attributes.impact().getModifiers().clear();
		attributes.impact().getModifiers().addAll(impact().getModifiers());

		attributes.impactAngle().setBaseValue(impactAngle().getBase());
		attributes.impactAngle().getModifiers().clear();
		attributes.impactAngle().getModifiers().addAll(impactAngle().getModifiers());

		attributes.range().setBaseValue(range().getBase());
		attributes.range().getModifiers().clear();
		attributes.range().getModifiers().addAll(range().getModifiers());

		attributes.value().setBaseValue(value().getBase());
		attributes.value().getModifiers().clear();
		attributes.value().getModifiers().addAll(value().getModifiers());

		ability.setMultiTarget(multiTarget);
	}

	public String playerSkin() {
		return playerSkin;
	}

	public Attribute<Integer> range() {
		return attributes.get(4);
	}

	public Attribute<Integer> value() {
		return attributes.get(5);
	}
}
