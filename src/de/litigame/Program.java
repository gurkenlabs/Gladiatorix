package de.litigame;

import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;
import de.litigame.items.Items;
import de.litigame.utilities.ImageUtilities;

public class Program {

	public static void main(String[] args) {
		Game.init(args);
		Resources.load("game.litidata");
		ImageUtilities.init(new File("images.txt"));
		Items.init(new File("items.json"));
		GameManager.init();
		Game.screens().add(new IngameScreen());
		Game.start();
	}
}
