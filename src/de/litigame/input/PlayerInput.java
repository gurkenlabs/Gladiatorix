package de.litigame.input;

import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.litigame.entities.Player;

public class PlayerInput {

	private static HotbarController hotbarController;
	private static MovementController movementController;

	public static void attach() {
		Input.keyboard().onKeyPressed(hotbarController);
	}

	public static void detach() {
		Input.keyboard().removeKeyPressedListener(hotbarController);
		Input.keyboard().removeKeyPressedListener(movementController);
	}

	public static void init() {
		Player player = Player.getInstance();

		movementController = new KeyboardEntityController<>(player);
		player.addController(movementController);

		hotbarController = new HotbarController(player.hotbar);
	}
}
