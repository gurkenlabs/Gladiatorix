package de.litigame.shop;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.items.Item;
import de.litigame.items.Items;

public class ShopEntry {

	public final boolean equippable;
	public final BufferedImage imageBuy, imageOwned;
	private final Item item;
	public final int price, reqLvl;

	public ShopEntry(Item item, int price, int reqLvl, boolean equippable) {
		this.item = item;
		this.price = price;
		this.reqLvl = reqLvl;
		this.equippable = equippable;
		imageBuy = Resources.images().get("ShopEntryBackgroundBuy");
		imageOwned = Resources.images().get("ShopEntryBackgroundOwned");
		final Graphics2D gBuy = imageBuy.createGraphics();
		final Graphics2D gOwned = imageOwned.createGraphics();
		gBuy.drawImage(item.getImage(), 0, 0, null);
		gOwned.drawImage(item.getImage(), 0, 0, null);
		gBuy.drawString(Integer.toString(price), 10, 10);
		gBuy.drawString(Integer.toString(reqLvl), 20, 10);
		gBuy.dispose();
	}

	public Item getItem() {
		return Items.getItem(item.getName());
	}
}
