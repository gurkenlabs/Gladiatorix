package de.litigame.gui;

import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.utilities.ImageUtilities;

public class MainMenuScreen extends Screen {

	public MainMenuScreen() {
		super("menu");

		String[] items = { "Start Game", "Load Game", "Settings", "Exit Game" };

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(),
				buttonImg.getHeight());

		Menu menu = new Menu(Game.window().getWidth() / 4, Game.window().getHeight() / 4, Game.window().getWidth() / 2,
				Game.window().getHeight() / 2, button, items);

		menu.prepare();
		menu.onChange(index -> {
			if (index == 0) Game.screens().display("ingame");
			else if (index == 2) Game.screens().display("settings");
			else if (index == 3) System.exit(0);
		});

		getComponents().add(bkgr);
		getComponents().add(menu);
	}
}
