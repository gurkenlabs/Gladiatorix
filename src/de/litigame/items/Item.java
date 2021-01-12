package de.litigame.items;

import java.awt.image.BufferedImage;
import java.util.Map;

import de.litigame.Images;

public class Item {

	protected BufferedImage image;
	protected String name;

	public Item(Map<String, String> itemInfo) {
		image = Images.get(itemInfo.get("item_image"));
		name = itemInfo.get("item_name");
	}

	public BufferedImage getImage() {
		return image;
	}

	public String getName() {
		return name;
	}
}
