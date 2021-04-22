package de.litigame;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.GameOverScreen;
import de.litigame.gui.IngameMenuScreen;
import de.litigame.gui.IngameScreen;
import de.litigame.gui.IngameSettingsScreen;
import de.litigame.gui.MainMenuScreen;
import de.litigame.gui.SettingsScreen;
import de.litigame.gui.TitleScreen;
import de.litigame.items.Items;
import de.litigame.shop.Shops;

public class Program {

  public static void main(String[] args) {
    Game.init(args);
    Resources.load("game.litidata");
    Items.init("resources/items.json");
    Shops.init("resources/shops.json");
    Game.screens().add(new TitleScreen());
    Game.screens().add(new GameOverScreen());
    Game.screens().add(new IngameScreen());
    Game.screens().add(new MainMenuScreen());
    Game.screens().add(new SettingsScreen());
    Game.screens().add(new IngameMenuScreen());
    Game.screens().add(new IngameSettingsScreen());
    Game.screens().display("title");
    GameManager.init();
    Game.start();
  }
}
