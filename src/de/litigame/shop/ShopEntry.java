package de.litigame.shop;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.items.Item;
import de.litigame.items.Items;

public class ShopEntry {

	public enum State {
		BUY, EQUIP
	}

	public final boolean equippable;
	private final BufferedImage[] images = { Resources.images().get("shopEntry_background_buy"),
			Resources.images().get("shopEntry_background_owned") };
	private final Item item;

	public final int price, requiredLevel;

	public ShopEntry(Item item, int price, int requiredLevel, boolean equippable) {
		this.item = item;
		this.price = price;
		this.requiredLevel = requiredLevel;
		this.equippable = equippable;
		drawImages();
	}

	private void drawImages() {
		Graphics2D g = getImage(State.BUY).createGraphics();
		g.drawImage(item.getImage(), 0, 0, null);
		g.drawString(Integer.toString(price), 10, 10);
		g.drawString(Integer.toString(requiredLevel), 20, 10);
		g.dispose();
		g = getImage(State.EQUIP).createGraphics();
		g.drawImage(item.getImage(), 0, 0, null);
		g.dispose();
	}

	public BufferedImage getImage(State state) {
		switch (state) {
		case BUY:
			return images[0];
		case EQUIP:
			return images[1];
		default:
			return null;
		}
	}

	public Item getItem() {
		return Items.getItem(item.getName());
	}
}
