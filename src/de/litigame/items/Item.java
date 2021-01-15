package de.litigame.items;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;

import de.litigame.Images;

public class Item {

	public static boolean isStackable(Item item) {
		if (item == null) return false;
		return Arrays.asList(item.getClass().getInterfaces()).contains(Stackable.class);
	}

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
