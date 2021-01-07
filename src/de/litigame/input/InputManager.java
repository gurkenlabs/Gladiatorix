package de.litigame.input;

import de.gurkenlabs.litiengine.input.Input;
import de.litigame.GameState;

public class InputManager {

	private static PlayerController playerController;

	public static void adjustInput(GameState state) {
		switch (state) {
		case INGAME:
			attachPlayerControl();
		case MAIN_MENU:
			break;
		case MENU:
			detachPlayerControl();
			break;
		case SETTINGS:
			break;
		default:
			break;
		}
	}

	public static void attachPlayerControl() {
		Input.keyboard().onKeyPressed(playerController);
	}

	public static void detachPlayerControl() {
		Input.keyboard().removeKeyPressedListener(playerController);
	}

	public static void init() {
		playerController = new PlayerController();
	}
}
