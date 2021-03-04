package de.litigame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.gui.IngameScreen;
import de.litigame.items.Items;

public class Program {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedAudioFileException, IOException  {
		Game.init(args);
		Resources.load("game.litidata");
		Images.init(new File("images.txt"));
		Items.init(new File("items.json"));
		Game.screens().add(new IngameScreen());
		GameManager.init();
		Game.start();
	}
}
