package de.litigame.shop;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.IMouse.MouseClickedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;

public class Shop implements IRenderable, KeyPressedListener, MouseClickedListener {

	private final List<ShopExitedListener> exitListeners = new ArrayList<>();
	private final List<ShopEntry> offers;
	private final List<ShopEntry> storage;

	public Shop(List<ShopEntry> offers, List<ShopEntry> storage) {
		this.offers = offers;
		this.storage = storage;
	}

	public void exit() {
		Input.keyboard().removeKeyPressedListener(this);
		Input.mouse().removeMouseClickedListener(this);
		((IngameScreen) Game.screens().get("ingame")).removeOverlayMenu(this);

		for (ShopExitedListener listener : exitListeners) listener.exit();
		exitListeners.clear();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) exit();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	public void open(ShopExitedListener... onExit) {
		exitListeners.addAll(Arrays.asList(onExit));

		Input.keyboard().onKeyPressed(this);
		Input.mouse().onClicked(this);
		((IngameScreen) Game.screens().get("ingame")).addOverlayMenu(this);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Resources.images().get("shop_background"), 0, 0, null);
	}
}
