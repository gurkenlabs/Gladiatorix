package de.litigame.shop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.litigame.items.Item;
import de.litigame.items.Items;
import de.litigame.utilities.ImageUtilities;

public class ShopEntry {

	public enum State {
		BUY, EQUIP
	}

	public final boolean equippable;
	private final BufferedImage[] images = { ImageUtilities.getRescaledCopy("shopEntry_buy", 2.5),
			ImageUtilities.getRescaledCopy("shopEntry_owned", 2.5) };
	private final Item item;

	public final int price, requiredLevel;

	public ShopEntry(Item item, int price, int requiredLevel, boolean equippable, String tooltip) {
		this.item = item;
		this.price = price;
		this.requiredLevel = requiredLevel;
		this.equippable = equippable;
		drawImages(tooltip);
	}

	private void drawImages(String info) {
		Graphics2D g = getImage(State.BUY).createGraphics();
		g.setColor(Color.BLACK);
		g.drawImage(ImageUtilities.getRescaledCopy(item.getImage(), 2), 20, 20, null);
		g.drawString(Integer.toString(price), 75, 35);
		g.drawString(Integer.toString(requiredLevel), 160, 35);
		g.drawString(info, 20, 10);
		g.dispose();
		g = getImage(State.EQUIP).createGraphics();
		g.drawImage(item.getImage(), 20, 20, null);
		g.drawString(info, 20, 10);
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
