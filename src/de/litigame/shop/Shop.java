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
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;
import de.litigame.entities.Player;
import de.litigame.gui.IngameScreen;
import de.litigame.items.Armor;
import de.litigame.items.Item;
import de.litigame.utilities.ImageUtilities;

public class Shop implements IRenderable, KeyPressedListener {

	public interface ShopExitedListener {
		void exit();
	}

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
		final ShopEntry selected = offers.get(index);
		if (buyer.canBuy(selected)) {
			buyer.buy(selected);
		}
		updateMenus();
	}

	private void equip(int index, Player buyer) {
		final ShopEntry selected = storage.get(index);
		final Item item = selected.getItem();
		GameManager.removeItemEntities(item);
		Player.getInstance().hotbar.removeItems(item);
		if (item instanceof Armor) {
			Player.getInstance().equip((Armor) item);
			Game.audio().playSound(Resources.sounds().get("equipArmor"));
		} else {
			Player.getInstance().hotbar.addItem(item);
			Game.audio().playSound(Resources.sounds().get("equipSword"));
		}
	}

	public void exit() {
		Input.keyboard().removeKeyPressedListener(this);
		offerMenu.suspend();
		storageMenu.suspend();
		((IngameScreen) Game.screens().get("ingame")).removeOverlayMenu(this);

		for (final ShopExitedListener listener : exitListeners) {
			listener.exit();
		}
		exitListeners.clear();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_E) {
			exit();
		}
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
		for (final ShopEntry entry : offers) {
			if (Player.getInstance().storage.contains(entry.getItem())) {
				offers.remove(entry);
				storage.add(entry);
			}
		}

		if (offerMenu != null) {
			offerMenu.suspend();
		}
		if (storageMenu != null) {
			storageMenu.suspend();
		}
		final double wOff = (Game.window().getResolution().width - background.getWidth()) / 2 + 56,
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
