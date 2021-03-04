package de.litigame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.*;
import de.litigame.items.Items;
import de.litigame.shop.Shops;
import de.litigame.utilities.ImageUtilities;

public class Program {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedAudioFileException, IOException  {
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
