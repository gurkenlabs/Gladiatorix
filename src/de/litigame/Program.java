package de.litigame;

import java.io.File;

import com.sun.tools.javac.Main;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.configuration.DisplayMode;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;
import de.litigame.gui.MainMenu;
import de.litigame.items.Items;

public class Program {

	public static void main(String[] args) {
		Game.init(args);
		Resources.load("game.litidata");
		Images.init(new File("images.txt"));
		Items.init(new File("items.json"));
		Game.config().graphics().setDisplayMode(DisplayMode.BORDERLESS);
		Game.screens().add(new IngameScreen());
		Game.screens().add(new MainMenu("menu_item"));
		Game.screens().display("menu");
		GameManager.init();
		Game.start();
	}
}
