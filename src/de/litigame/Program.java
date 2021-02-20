package de.litigame;

import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;
import de.litigame.gui.MainMenuScreen;
import de.litigame.gui.SettigsScreen;
import de.litigame.items.Items;
import de.litigame.utilities.ImageUtilities;

public class Program {

	public static void main(String[] args) {
		Game.init(args);
		Resources.load("game.litidata");
		ImageUtilities.init(new File("images.txt"));
		Items.init(new File("items.json"));
		Game.screens().add(new IngameScreen());
		Game.screens().add(new MainMenuScreen());
		Game.screens().add(new SettigsScreen("menu_item", "Done"));
		Game.screens().display("menu");
		GameManager.init();
		Game.start();
	}
}
