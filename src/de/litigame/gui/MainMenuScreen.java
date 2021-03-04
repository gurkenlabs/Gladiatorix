package de.litigame.gui;

import java.awt.image.BufferedImage;

import javax.sound.sampled.LineUnavailableException;
import javax.xml.bind.JAXBException;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.configuration.SoundConfiguration;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.SoundPlayback;
import de.gurkenlabs.litiengine.util.io.XmlUtilities;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.entities.Player;
import de.litigame.utilities.ImageUtilities;

public class MainMenuScreen extends Screen {

	public MainMenuScreen() throws LineUnavailableException {
		super("menu");

		SaveGame saveGame = new SaveGame();

		String[] items = { "Spiel Starten", "Spiel Laden", "Einstellungen", "Spiel Schliessen" };

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(), buttonImg.getHeight());

		Menu menu = new Menu((double) (Game.window().getWidth() - buttonImg.getWidth()) / 2, (double) (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2, buttonImg.getWidth(), buttonImg.getHeight() * items.length, button, items);

		menu.onChange(index -> {
			if (index == 0) {
				String[] initialItems = { "bow", "sword", "null", "null", "null", "null", "null", "null", "null" };
				Player.getInstance().init(initialItems, 0, 1, Game.world().environment().getSpawnpoint("spawn").getLocation(), Player.getInstance().getHitPoints().getMax(), 0);
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
		Game.config().sound().setMusicVolume(GameManager.volume);
		Game.audio().playMusic(Resources.sounds().get("sounds/menu.mp3"));
		getComponents().add(bkgr);
		getComponents().add(menu);
	}

//loadSavedGameFile(): Loads values from xml file

	public void loadSavedGameFile() {
		try {
			String path = "savegames/" + "jacob" + ".xml";
			final SaveGame saveGame = XmlUtilities.read(SaveGame.class, Resources.getLocation(path));
			Player.getInstance().init(saveGame.getHotbar(), saveGame.getHealth(), saveGame.getMoney(), saveGame.getLocation(), saveGame.getHealth(), saveGame.getSlot());
		} catch (JAXBException e) {
		}

	}

	@Override
	public void suspend() {
		super.suspend();
		Game.audio().stopMusic();
	}
}
