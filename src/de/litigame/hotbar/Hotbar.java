package de.litigame.hotbar;

public class Hotbar {

	public final static int SLOTS = 9;
	private int selectedSlot;

	public void addToPosition(int shift) {
		selectedSlot += shift;
		checkBounds();
	}

	private void checkBounds() {
		if (selectedSlot < 0) selectedSlot = 0;
		else if (selectedSlot >= SLOTS) selectedSlot = SLOTS - 1;
	}

	public void setToSlot(int slot) {
		selectedSlot = slot;
		checkBounds();
	}
}
