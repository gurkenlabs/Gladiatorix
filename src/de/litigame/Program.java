package de.litigame;

import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;
import de.litigame.items.Items;
import de.litigame.shop.Shops;
import de.litigame.utilities.ImageUtilities;

public class Program {

	public static void main(String[] args) {
		Game.init(args);
		Resources.load("game.litidata");
		ImageUtilities.init(new File("images.txt"));
		Items.init(new File("items.json"));
		Shops.init(new File("shops.json"));
		Game.screens().add(new IngameScreen());
		GameManager.init();
		Game.start();
	}
}
