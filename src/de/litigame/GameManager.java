package de.litigame;

import java.awt.event.KeyEvent;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.PropMapObjectLoader;
import de.gurkenlabs.litiengine.input.Input;
import de.litigame.entities.Player;
import de.litigame.entities.Portal;
import de.litigame.graphics.PlayerCamera;
import de.litigame.input.InputManager;

public class GameManager {

	public static void enterPortal(Portal portal) {
		switchToMap(portal.getProperties().getProperty("toMap").getAsString());
	}

	public static void init() {

		PropMapObjectLoader.registerCustomPropType(Portal.class);

		InputManager.init();

		Game.world().setCamera(new PlayerCamera());

		switchToMap("map1");

		// just for now
		Input.keyboard().onKeyPressed(KeyEvent.VK_X, e -> InputManager.adjustInput(GameState.MENU));
		Input.keyboard().onKeyPressed(KeyEvent.VK_E, e -> InputManager.adjustInput(GameState.INGAME));

		switchToState(GameState.INGAME);
		//
	}

	public static void switchToMap(String map) {
		Game.world().loadEnvironment(map);
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());
	}

	public static void switchToState(GameState state) {
		InputManager.adjustInput(state);
	}
}
