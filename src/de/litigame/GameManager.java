package de.litigame;

import java.awt.event.KeyEvent;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.PropMapObjectLoader;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.Input;
import de.litigame.entities.Player;
import de.litigame.entities.Portal;
import de.litigame.input.InputManager;

public class GameManager {

	private static GameState currentState;

	public static void enterPortal(Portal portal) {
		switchToMap(portal.getProperties().getProperty("toMap").getAsString());
	}

	public static void init() {

		Input.keyboard().onKeyPressed(KeyEvent.VK_X, e -> InputManager.adjustInput(GameState.MENU));
		Input.keyboard().onKeyPressed(KeyEvent.VK_E, e -> InputManager.adjustInput(GameState.INGAME));

		currentState = GameState.INGAME;

		PropMapObjectLoader.registerCustomPropType(Portal.class);

		InputManager.init();
		InputManager.adjustInput(currentState);

		Camera cam = new PositionLockCamera(Player.getInstance());

		Game.world().setCamera(cam);

		switchToMap("map1");
	}

	public static void switchToMap(String map) {
		Game.world().loadEnvironment(map);
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());
	}
}
