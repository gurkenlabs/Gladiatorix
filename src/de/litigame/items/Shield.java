package de.litigame.items;

import java.util.Map;

import de.gurkenlabs.litiengine.attributes.AttributeModifier;
import de.gurkenlabs.litiengine.attributes.Modification;

public class Shield extends Item {
	private final AttributeModifier<Integer> healthBuff;

	public Shield(Map<String, String> itemInfo) {
		super(itemInfo);
		healthBuff = new AttributeModifier<>(Modification.ADD,
				itemInfo.containsKey("shield_buff") ? Double.valueOf(itemInfo.get("shield_buff")) : 0);
	}

	public AttributeModifier<Integer> healthBuff() {
		return healthBuff;
	}
}
