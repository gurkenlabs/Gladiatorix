package de.litigame.weapons;

import java.util.Map;

import de.litigame.items.Item;

public class Weapon extends Item {

	private static final String[] attributeKeys = { "weapon_cooldown", "weapon_duration", "weapon_impact",
			"weapon_impactAngle", "weapon_range", "weapon_value" };
	public final WeaponAttributes attributes = new WeaponAttributes();
	public final WeaponType type;

	public Weapon(Map<String, String> itemInfo) {
		for (int i = 0; i < attributeKeys.length; ++i) {
			String key = attributeKeys[i];
			if (itemInfo.containsKey(key)) attributes.list.get(i).setBaseValue(Integer.valueOf(itemInfo.get(key)));
		}
		switch (itemInfo.get("weapon_type")) {
		case "melee":
			type = WeaponType.MELEE;
			break;
		case "range":
			type = WeaponType.RANGE;
			break;
		default:
			type = null;
		}
	}
}
