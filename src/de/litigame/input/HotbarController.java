package de.litigame.input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.util.ListUtilities;
import de.litigame.hotbar.Hotbar;

public class HotbarController implements KeyPressedListener {

	private final Hotbar bar;
	private final List<Integer>[] slotKeys = new List[Hotbar.SLOTS];

	public HotbarController(Hotbar hotbar) {
		this(hotbar, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
				KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9);
	}

	public HotbarController(Hotbar hotbar, int... hot) {
		bar = hotbar;

		if (hot.length != Hotbar.SLOTS) throw new RuntimeException(
				"Parameter size (" + hot.length + ") and hotbar slots (" + Hotbar.SLOTS + ") don't match");

		for (int i = 0; i < Hotbar.SLOTS; ++i) {
			slotKeys[i] = new ArrayList<>();

			slotKeys[i].add(hot[i]);
		}
	}

	public void addHotKey(int index, int keyCode) {
		slotKeys[index].add(keyCode);
	}

	public List<Integer> getHotKeys(int index) {
		return slotKeys[index];
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();

		for (int slot = 0; slot < slotKeys.length; ++slot) {
			List<Integer> keys = slotKeys[slot];

			if (keys.contains(key)) {
				bar.setToSlot(slot);
				break;
			}
		}
	}

	public void setHotKeys(int index, int... hot) {
		setHotKeys(index, ListUtilities.getIntList(hot));
	}

	public void setHotKeys(int index, List<Integer> hot) {
		slotKeys[index] = hot;
	}
}
