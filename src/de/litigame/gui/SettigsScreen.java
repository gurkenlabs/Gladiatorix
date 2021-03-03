package de.litigame.gui;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.XmlUtilities;
import de.litigame.SaveGame;
import de.litigame.entities.Player;
import de.litigame.utilities.ImageUtilities;

public class SettigsScreen extends Screen {

	public SettigsScreen() {
		super("settings");

		String[] items = { "Save Game", "Done" };

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(),
				buttonImg.getHeight());

		Menu settings = new Menu(Game.window().getWidth() / 4, Game.window().getHeight() / 4, Game.window().getWidth() / 2,
				Game.window().getHeight() / 2, button, items);

		settings.prepare();
		settings.onChange(index -> {
			System.out.println(index+ ", "+settings.getName());
			if (index == 0) saveGame(); System.out.println("yes");
			if (index == 1) Game.screens().display("menu");
		});

		getComponents().add(bkgr);
		getComponents().add(settings);
	}


	public File saveGame () {
		SaveGame saveGame = new SaveGame(Player.getInstance().getMoney(),
				Player.getInstance().getLvl(), Player.getInstance().getLocation(), Player.getInstance().getHitPoints().get(), "jacob");
		String dir = "savegames/";
		File dirFile = new File(dir);
		dirFile.mkdirs();
		String saveGamePath = dir + saveGame.getName() + ".xml";
		return(XmlUtilities.save(saveGame, saveGamePath));
	}
}
