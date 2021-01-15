package de.litigame.input;

import de.gurkenlabs.litiengine.input.Input;
import de.litigame.GameState;

public class InputManager {

	private final static PlayerController playerController = new PlayerController();

	public static void adjustInput(GameState state) {
		switch (state) {
		case INGAME:
			attachPlayerControl();
			break;
		case MAIN_MENU:
			detachPlayerControl();
			break;
		case MENU:
			detachPlayerControl();
			break;
		case SETTINGS:
			detachPlayerControl();
			break;
		default:
			break;
		}
	}

	public static void attachPlayerControl() {
		Input.keyboard().onKeyPressed(playerController);
		Input.mouse().onWheelMoved(playerController);
	}

	public static void detachPlayerControl() {
		Input.keyboard().removeKeyPressedListener(playerController);
		Input.mouse().removeMouseWheelListener(playerController);
	}

	public static void init() {
	}

	private InputManager() {
	}
}
