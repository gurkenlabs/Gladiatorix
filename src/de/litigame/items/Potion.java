package de.litigame.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.attributes.AttributeModifier;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.litigame.entities.Player;
import de.litigame.items.Item.Consumable;
import de.litigame.items.Item.Stackable;

public class Potion extends Item implements Stackable, Consumable {

	private int amount = 1;

	List<ConsumeListener> consumeListeners = new ArrayList<>();

	private AttributeModifier mod;

	public Potion(Map<String, String> itemInfo) {
		super(itemInfo);
		switch (itemInfo.get("potion_effect")) {
		case "heal":
			mod = new AttributeModifier<Integer>(Modification.ADD, 50);
			addConsumeListener(p -> p.getHitPoints().addModifier(mod));
			break;
		case "speed":
			mod = new AttributeModifier<Float>(Modification.MULTIPLY, 2);
			addConsumeListener(p -> p.getVelocity().addModifier(mod));
			Game.loop().perform(5000, () -> Player.getInstance().getVelocity().removeModifier(mod));
			break;
		}
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public List<ConsumeListener> getConsumeListeners() {
		return consumeListeners;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
