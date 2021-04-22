package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TitleScreen extends Screen {

  private ImageComponent startGame;

  public TitleScreen() {
    super("title");
  }

  @Override
  public void render(Graphics2D g) {
    super.render(g);
    g.drawImage(Resources.images().get("title.png"), 0, 0, Game.window().getWidth(), Game.window().getHeight(), null);
    startGame.render(g);
  }

  @Override protected void initializeComponents() {
    super.initializeComponents();
    BufferedImage button = Resources.images().get("sound_bar.png");
    startGame = new ImageComponent((Game.window().getWidth() - button.getWidth()) / 2d, (Game.window().getHeight() - button.getHeight()) - 100,
        button);
    startGame.setText("Spiel Starten");
    startGame.onClicked(event -> {
      suspend();
      Game.screens().display("menu");
    });
    startGame.setHoverSound(Resources.sounds().get("mouse-over.wav"));
    getComponents().add(startGame);
  }
}
