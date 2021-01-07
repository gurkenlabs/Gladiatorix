package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;

public class Program {

	public static void main(String[] args) {
		Game.init(args);
		Resources.load("game.litidata");
		Images.loadImages("images.txt");
		Game.screens().add(new IngameScreen());
		GameManager.init();
		Game.start();
	}
}
