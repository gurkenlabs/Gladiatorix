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
		/*
		 * for (IMobileEntity e : Game.world().environment().getMobileEntities()) { if
		 * (e instanceof Enemy) { ((Enemy)
		 * e).getController(EnemyController.class).nav.render(g); ((AStarPathFinder)
		 * ((Enemy)
		 * e).getController(EnemyController.class).nav.getPathFinder()).getGrid()
		 * .render(g); } }
		 */
		Player.getInstance().hotbar.render(g);
	}
}
