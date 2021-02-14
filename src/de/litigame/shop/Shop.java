package de.litigame.shop;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.litigame.entities.Player;
import de.litigame.gui.IngameScreen;
import de.litigame.utilities.ImageUtilities;

public class Shop implements IRenderable, KeyPressedListener {

	private final BufferedImage background;
	private final List<ShopExitedListener> exitListeners = new ArrayList<>();
	private ShopEntryMenu offerMenu, storageMenu;
	private final List<ShopEntry> offers, storage;

	public Shop(List<ShopEntry> offers, List<ShopEntry> storage, BufferedImage background) {
		this.background = ImageUtilities.getRescaledCopy(background, 2.5);
		this.offers = offers;
		this.storage = storage;
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
		((IngameScreen) Game.screens().get("ingame")).addOverlayMenu(this);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(background, (Game.window().getResolution().width - background.getWidth()) / 2,
				(Game.window().getResolution().height - background.getHeight()) / 2, null);
		offerMenu.render(g);
		storageMenu.render(g);
	}

	private void updateMenus() {
		if (offerMenu != null) offerMenu.suspend();
		if (storageMenu != null) storageMenu.suspend();
		double wOff = (Game.window().getResolution().width - background.getWidth()) / 2 + 56,
				hOff = (Game.window().getResolution().height - background.getHeight()) / 2 + 140;
		offerMenu = new ShopEntryMenu(wOff, hOff, offers, ShopEntry.State.BUY);
		storageMenu = new ShopEntryMenu(Game.window().getResolution().width - wOff - offerMenu.getWidth(), hOff,
				storage, ShopEntry.State.EQUIP);
		offerMenu.onChange(i -> buy(i, Player.getInstance()));
		storageMenu.onChange(i -> equip(i, Player.getInstance()));
		offerMenu.prepare();
		storageMenu.prepare();
	}
}
