package de.litigame.gui;

import java.awt.*;

import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.litigame.entities.Player;
import de.litigame.hp.EnemyHealthBar;
import de.litigame.hp.PlayerHealthBar;

public class IngameScreen extends GameScreen {

	public IngameScreen() {
		super("ingame");
	}
	private PlayerHealthBar hpb;
	@Override
	public void render(Graphics2D g) {
		hpb = new PlayerHealthBar(Player.getInstance());
		super.render(g);

		hpb.render(g);
		Player.getInstance().hotbar.render(g);
	}
}
