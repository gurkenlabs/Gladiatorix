package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.environment.PropMapObjectLoader;

public class GameManager {

	public static void switchToMap(String map) {
		Game.world().loadEnvironment(map);
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());

	}

	public static void enterPortal(Prop portal) {
		switchToMap(portal.getProperties().getProperty("toMap").getAsString());
	}

	public static void init() {
		PropMapObjectLoader.registerCustomPropType(Portal.class);
		switchToMap("map1");
	}
}
