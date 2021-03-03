package de.litigame.gui;

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

import javax.xml.bind.JAXBException;

public class MainMenuScreen extends Screen {

	public MainMenuScreen() {
		super("menu");

		SaveGame saveGame = new SaveGame();

		String[] items = { "Start Game", "Load Game", "Settings", "Exit Game" };

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(),
				buttonImg.getHeight());

		Menu menu = new Menu(
				(double) (Game.window().getWidth()-buttonImg.getWidth()) / 2,
				(double) (Game.window().getHeight()-buttonImg.getHeight()*items.length) / 2,
				buttonImg.getWidth(),
				buttonImg.getHeight()*items.length, button, items);

		menu.onChange(index -> {
			if (index == 0) {
				String[] initialItems = {"bow","sword","null" ,"null" ,"null" ,"null" ,"null" ,"null" ,"null"};
				Player.getInstance().init(initialItems,0,1,
						Game.world().environment().getSpawnpoint("spawn").getLocation(),
						Player.getInstance().getHitPoints().getMax(), 0);
				saveGame.saveGame();
				Game.screens().display("ingame");
			}
			if (index == 1) {
				loadSavedGameFile();
				Game.screens().display("ingame");
			}
			if (index == 2) Game.screens().display("settings");
			if (index == 3) System.exit(0);
		});

		getComponents().add(bkgr);
		getComponents().add(menu);
	}

//loadSavedGameFile(): Loads values from xml file

	public void loadSavedGameFile() {
		try {
			String path = "savegames/" + "jacob" + ".xml";
			final SaveGame saveGame = XmlUtilities.read(SaveGame.class, Resources.getLocation(path));
			Player.getInstance().init(saveGame.getHotbar(), saveGame.getHealth(), saveGame.getMoney(),saveGame.getLocation(), saveGame.getHealth(), saveGame.getSlot());
		}
		catch (JAXBException e) {
		}

	}
}
