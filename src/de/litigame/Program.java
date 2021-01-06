package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

public class Program {

	public static void main(String[] args) {
		Game.init(args);
		Resources.load("game.litidata");
		Images.loadImages("images.txt");
		Game.screens().add(new IngameScreen());
		test();
		Game.start();
	}

	private static void test() {
		Game.world().loadEnvironment("map1");
		Game.world().getEnvironment("map1").getSpawnpoint("spawn").spawn(Player.getInstance());
	}
}
