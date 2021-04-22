package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import de.gurkenlabs.litiengine.util.io.XmlUtilities;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.entities.Player;

import javax.xml.bind.JAXBException;
import java.awt.image.BufferedImage;

public class MainMenuScreen extends Screen {

  private ImageComponent bkgr;
  private Menu menu;

  public MainMenuScreen() {
    super("menu");
  }

  //loadSavedGameFile(): Loads values from xml file

  public void loadSavedGameFile(String name) {
    try {
      final String path = name + ".xml";
      final SaveGame saveGame = XmlUtilities.read(SaveGame.class, Resources.getLocation(path));
      System.out.println(saveGame.getMoney());
      Player.getInstance()
          .init(saveGame.getHotbar(), saveGame.getMoney(), saveGame.getHealth(), saveGame.getLocation(), saveGame.getHealth(), saveGame.getSlot());
    } catch (final JAXBException e) {
      System.err.println("Savegame could not be loaded.");
    }

  }

  @Override
  public void prepare() {
    super.prepare();
    Game.audio().playMusic(Resources.sounds().get("menu.wav"));
  }

  @Override protected void initializeComponents() {
    super.initializeComponents();
    final BufferedImage buttonImg = Imaging.scale(Resources.images().get("menu_item.png"), .5f);
    final Spritesheet button = new Spritesheet(buttonImg, "menu_item.png", buttonImg.getWidth(), buttonImg.getHeight());
    final String[] items = { "Neues Spiel", "Fortfahren", "Einstellungen", "Spiel Schliessen" };
    bkgr = new ImageComponent(0, 0, Resources.images().get("menu.png"));
    menu = new Menu((Game.window().getWidth() - buttonImg.getWidth()) / 2d,
        (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2d, buttonImg.getWidth(), buttonImg.getHeight() * items.length,
        button, items);
    menu.onChange(index -> {
      if (index == 0) {
        final String[] initialItems = { "Trainingsschwert", "null", "null", "null", "null" };
        System.out.println(GameManager.spawn.getX());
        Player.getInstance().init(initialItems, 0, 1, GameManager.spawn, Player.getInstance().getHitPoints().getMax(), 0);
        Game.screens().display("ingame");
      }
      if (index == 1) {
        loadSavedGameFile("savegame");
        suspend();
        Game.screens().display("ingame");
      }
      if (index == 2) {
        Game.screens().display("settings");
      }
      if (index == 3) {
        System.exit(0);
      }
    });
    for (ImageComponent cell : menu.getCellComponents()) {
      cell.setFont(GameManager.minecraft.deriveFont(32f));
      cell.setHoverSound(Resources.sounds().get("mouse-over.wav"));
    }
    getComponents().add(bkgr);
    getComponents().add(menu);
  }
}
