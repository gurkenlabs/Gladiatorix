package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.PropMapObjectLoader;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.litigame.entities.Player;
import de.litigame.entities.Portal;

public class GameManager {

	public enum GameState {
		INGAME, MAIN_MENU, MENU, SETTINGS;
	}

	public static void enterPortal(Portal portal) {
		switchToMap(portal.getProperties().getProperty("toMap").getAsString());
	}

	public static void init() {
		PropMapObjectLoader.registerCustomPropType(Portal.class);

		Camera cam = new PositionLockCamera(Player.getInstance());

		Game.world().setCamera(cam);

		switchToMap("map1");
	}

	public static void switchToMap(String map) {
		Game.world().loadEnvironment(map);
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());
	}
}
