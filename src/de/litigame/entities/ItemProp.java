package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.litigame.items.Item;

public class ItemProp extends Prop implements IInteractEntity {

	public final Item item;

	public ItemProp(Item item) {
		super("item");
		addToWorld();

		this.item = item;
		animations().add(new Animation(item.getSprite(), false, false, 0));
	}

	@Override
	public void interact(Player player) {
		if (player.hotbar.addItem(item)) removeFromWorld();
	}
}
