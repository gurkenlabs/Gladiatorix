package de.litigame.hotbar;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.litigame.Images;
import de.litigame.items.Item;
import de.litigame.items.Stackable;

public class Hotbar implements IRenderable {

	private final Item[] items;
	private int selectedSlot = 0;

	public Hotbar() {
		this(9);
	}

	public Hotbar(int size) {
		items = new Item[size];
	}

	public boolean addItem(Item other) {
		for (int i = 0; i < items.length; ++i) {
			if (items[i] == null) {
				items[i] = other;
				removeEmptyItems();
				return true;
			} else if (items[i].getName().equals(other.getName()) && Item.isStackable(other)
					&& ((Stackable) items[i]).addAmount(((Stackable) other).getAmount())) {
						removeEmptyItems();
						return true;
					}
		}
		return false;
	}

	public void addToPosition(int shift) {
		selectedSlot += shift;
		checkBounds();
	}

	private void checkBounds() {
		if (selectedSlot < 0) selectedSlot = 0;
		else if (selectedSlot >= size()) selectedSlot = size() - 1;
	}

	public Item getSelectedItem() {
		return items[selectedSlot];
	}

	private void removeEmptyItems() {
		for (int i = 0; i < items.length; ++i) {
			if (Item.isStackable(items[i]) && ((Stackable) items[i]).isEmpty()) items[i] = null;
		}
	}

	@Override
	public void render(Graphics2D graphics) {
		BufferedImage slot = Images.get("slot");
		BufferedImage image = new BufferedImage(slot.getWidth() * size(), slot.getHeight(), slot.getType());
		Graphics2D g = image.createGraphics();

		for (int i = 0; i < size(); ++i) {
			int x = i * slot.getWidth(), y = 0;
			g.drawImage(slot, x, y, null);
			if (i == selectedSlot) g.drawImage(Images.get("selected_slot"), x, y, null);
			if (items[i] != null) g.drawImage(items[i].getImage(), x, y, null);
		}

		g.dispose();

		image = Images.getRescaledCopy(image, 3);

		graphics.drawImage(image, (Game.window().getWidth() - image.getWidth()) / 2,
				Game.window().getHeight() - image.getHeight(), null);
		System.out.println(Game.window().getWidth() + "  " + Game.window().getHeight());

	}

	public void setToSlot(int slot) {
		selectedSlot = slot;
		checkBounds();
	}

	public int size() {
		return items.length;
	}
}
