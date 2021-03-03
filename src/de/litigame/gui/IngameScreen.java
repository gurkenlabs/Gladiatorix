package de.litigame.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Input;
import de.litigame.entities.Player;

public class IngameScreen extends GameScreen{

	private final List<IRenderable> overlayMenus = new ArrayList<>();

	public IngameScreen() {
		super("ingame");
		Input.keyboard().onKeyPressed(this::handlePressedKey);
	}

	public void handlePressedKey(final KeyEvent keyCode) {
		if(keyCode.getKeyCode()==KeyEvent.VK_ESCAPE && isVisible()){
			Game.screens().display("ingameMenu");
		}
	}

	public void addOverlayMenu(IRenderable menu) {
		overlayMenus.add(menu);
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
}
