package de.litigame.hotbar;

import java.awt.Graphics2D;

import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.litigame.items.Item;

public class Hotbar implements IRenderable {

	private final Item[] items;
	private int selectedSlot = 0;

	public Hotbar() {
		this(9);
	}

	public Hotbar(int size) {
		items = new Item[size];
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
