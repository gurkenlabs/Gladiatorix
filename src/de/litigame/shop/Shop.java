package de.litigame.shop;

import java.awt.Graphics2D;
import java.util.List;

import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;

public class Shop implements IRenderable {
	private final List<ShopEntry> offers;
	private final List<ShopEntry> storage;

	public Shop(List<ShopEntry> offers, List<ShopEntry> storage) {
		this.offers = offers;
		this.storage = storage;
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Resources.images().get("ShopUI"), 0, 0, null);
	}
}
