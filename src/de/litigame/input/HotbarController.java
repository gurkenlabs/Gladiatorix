package de.litigame.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.util.ListUtilities;
import de.litigame.hotbar.Hotbar;

public class HotbarController implements KeyPressedListener, MouseWheelListener {

	private final Hotbar bar;
	public boolean invertMouseWheel = false;
	private final List<List<Integer>> slotKeys = new ArrayList<>();

	public HotbarController(Hotbar hotbar) {
		this(hotbar, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
				KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9);
	}

	public HotbarController(Hotbar hotbar, int... hot) {
		bar = hotbar;

		if (hot.length != hotbar.size()) throw new RuntimeException(
				"Parameter size (" + hot.length + ") and hotbar slots (" + hotbar.size() + ") don't match");

		for (int i = 0; i < bar.size(); ++i) {
			slotKeys.add(new ArrayList<>());

			addHotKey(i, hot[i]);
		}
	}

	public void addHotKey(int index, int keyCode) {
		slotKeys.get(index).add(keyCode);
	}

	public List<Integer> getHotKeys(int index) {
		return slotKeys.get(index);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();

		for (int slot = 0; slot < slotKeys.size(); ++slot) {
			List<Integer> keys = slotKeys.get(slot);

			if (keys.contains(key)) {
				bar.setToSlot(slot);
				break;
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		if (invertMouseWheel) {
			bar.addToPosition(-event.getWheelRotation());
		} else {
			bar.addToPosition(event.getWheelRotation());
		}
	}

	public void setHotKeys(int index, int... hot) {
		setHotKeys(index, ListUtilities.getIntList(hot));
	}

	public void setHotKeys(int index, List<Integer> hot) {
		slotKeys.set(index, hot);
	}
}
