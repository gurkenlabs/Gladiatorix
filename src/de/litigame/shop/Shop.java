package de.litigame.shop;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Player;
import de.litigame.gui.IngameScreen;

public class Shop implements IRenderable, KeyPressedListener {

	private final List<ShopExitedListener> exitListeners = new ArrayList<>();
	private final ShopEntryMenu offerMenu = new ShopEntryMenu(100, 100, 100, 100, ShopEntry.State.BUY),
			storageMenu = new ShopEntryMenu(400, 100, 100, 100, ShopEntry.State.EQUIP);
	private final List<ShopEntry> offers, storage;

	public Shop(List<ShopEntry> offers, List<ShopEntry> storage) {
		this.offers = offers;
		this.storage = storage;

		offerMenu.onChange(i -> buy(i, Player.getInstance()));
		storageMenu.onChange(i -> equip(i, Player.getInstance()));
	}

	private void buy(int index, Player buyer) {
		ShopEntry selected = offers.get(index);
		if (buyer.canBuy(selected)) {
			if (selected.equippable) {
				offers.remove(selected);
				storage.add(selected);
			}
			buyer.hotbar.giveItem(selected.getItem());
		}
		updateMenus();
	}

	private void equip(int index, Player buyer) {

	}

	public void exit() {
		Input.keyboard().removeKeyPressedListener(this);
		offerMenu.suspend();
		storageMenu.suspend();
		((IngameScreen) Game.screens().get("ingame")).removeOverlayMenu(this);

		for (ShopExitedListener listener : exitListeners) listener.exit();
		exitListeners.clear();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) exit();
	}

	public void open(ShopExitedListener... onExit) {
		exitListeners.addAll(Arrays.asList(onExit));

		Input.keyboard().onKeyPressed(this);
		updateMenus();
		/*
		 * offerMenu.prepare(); storageMenu.prepare();
		 */
		((IngameScreen) Game.screens().get("ingame")).addOverlayMenu(this);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Resources.images().get("shop_background"), 0, 0, null);
		offerMenu.render(g);
		storageMenu.render(g);
	}

	private void updateMenus() {
		offerMenu.updateEntries(offers);
		storageMenu.updateEntries(storage);
	}
}
