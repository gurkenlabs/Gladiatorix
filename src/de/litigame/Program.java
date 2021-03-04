package de.litigame;

import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.*;
import de.litigame.items.Items;
import de.litigame.shop.Shops;
import de.litigame.utilities.ImageUtilities;

import javax.sound.sampled.LineUnavailableException;

public class Program {

	public static void main(String[] args) throws LineUnavailableException {
		Game.init(args);
		Resources.load("game.litidata");
		ImageUtilities.init(new File("images.txt"));
		Items.init(new File("items.json"));
		Shops.init(new File("shops.json"));
		Game.screens().add(new IngameScreen());
		Game.screens().add(new MainMenuScreen());
		Game.screens().add(new SettingsScreen());
		Game.screens().add(new IngameMenuScreen());
		Game.screens().add(new IngameSettingsScreen());
		Game.screens().display("menu");
		GameManager.init();
		Game.start();
	}
}
