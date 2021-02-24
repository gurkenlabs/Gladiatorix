package de.litigame.items;

import java.util.Map;

import de.gurkenlabs.litiengine.attributes.AttributeModifier;
import de.gurkenlabs.litiengine.attributes.Modification;

public class Armor extends Item {

	private final AttributeModifier<Integer> healthBuff;
	private final String playerSkin;

	public Armor(Map<String, String> itemInfo) {
		super(itemInfo);
		healthBuff = new AttributeModifier<>(Modification.ADD,
				itemInfo.containsKey("armor_buff") ? Double.valueOf(itemInfo.get("armor_buff")) : 0);
		playerSkin = itemInfo.containsKey("armor_skin") ? itemInfo.get("armor_skin") : "";
	}

	public String getPlayerSkin() {
		return playerSkin;
	}

	public AttributeModifier<Integer> healthBuff() {
		return healthBuff;
	}
}
