package de.litigame.hotbar;

import java.awt.Graphics2D;

import de.gurkenlabs.litiengine.graphics.IRenderable;
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
	public void render(Graphics2D g) {

	}

	public void setToSlot(int slot) {
		selectedSlot = slot;
		checkBounds();
	}

	public int size() {
		return items.length;
	}
}
