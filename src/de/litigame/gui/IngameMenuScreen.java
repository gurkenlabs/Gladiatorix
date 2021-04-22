package de.litigame.gui;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.SaveGame;

public class IngameMenuScreen extends Screen implements KeyPressedListener {
  SaveGame saveGame = new SaveGame();

  private Menu menu;

  public IngameMenuScreen() {
    super("ingameMenu");
  }

  @Override
  public void keyPressed(KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
      Game.screens().display("ingame");
  }

  @Override
  public void prepare() {
    super.prepare();

    Input.keyboard().onKeyPressed(this);
  }

  @Override
  public void suspend() {
    super.suspend();
    Input.keyboard().removeKeyPressedListener(this);
  }

  @Override protected void initializeComponents() {
    super.initializeComponents();

    String[] items = { "Fortfahren", "Spiel speichern", "Zurueck zum Titel" };

    ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu.png"));

    BufferedImage buttonImg = Resources.images().get("menu_item.png");

    Spritesheet button = new Spritesheet(buttonImg, "menu_item", buttonImg.getWidth(), buttonImg.getHeight());

    menu = new Menu((Game.window().getWidth() - buttonImg.getWidth()) / 2d, (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2d,
        buttonImg.getWidth(), buttonImg.getHeight() * items.length, button, items);

    menu.onChange(index -> {
      if (index == 0)
        Game.screens().display("ingame");
      if (index == 1)
        saveGame.saveGame();
      if (index == 2)
        Game.screens().display("menu");
    });
    for (ImageComponent cell : menu.getCellComponents()) {
      cell.setHoverSound(Resources.sounds().get("mouse-over.wav"));
    }
    getComponents().add(bkgr);
    getComponents().add(menu);
  }
}
