package de.litigame.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.litigame.entities.Player;

public class IngameScreen extends GameScreen implements KeyPressedListener {

	private final List<IRenderable> overlayMenus = new ArrayList<>();

	public IngameScreen() {
		super("ingame");
	}

	public void addOverlayMenu(IRenderable menu) {
		overlayMenus.add(menu);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) Game.screens().display("ingameMenu");

	}

	@Override
	public void prepare() {
		super.prepare();
		Input.keyboard().onKeyPressed(this);
	}

	public void removeOverlayMenu(IRenderable menu) {
		overlayMenus.remove(menu);
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);

		Player.getInstance().healthBar.render(g);
		Player.getInstance().hotbar.render(g);

		for (IRenderable menu : overlayMenus) menu.render(g);
	}

	@Override
	public void suspend() {
		super.suspend();
		Input.keyboard().removeKeyPressedListener(this);
	}
}
