package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.litigame.items.Item;

public class ItemProp extends Prop {

	public final Item item;

	public ItemProp(Item item) {
		super("item");

		this.item = item;

		animations().add(new Animation(item.getSprite(), false, false, 0));
	}
}
