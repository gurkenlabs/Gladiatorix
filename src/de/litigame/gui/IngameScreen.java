package de.litigame.gui;

import java.awt.Graphics2D;

import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.litigame.entities.Player;

public class IngameScreen extends GameScreen {

	public IngameScreen() {
		super("ingame");
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		Player.getInstance().hotbar.render(g);
	}
}
