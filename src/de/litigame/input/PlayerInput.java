package de.litigame.input;

import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.litigame.entities.Player;

public class PlayerInput {
	public static void init() {
		Player player = Player.getInstance();
		player.addController(new KeyboardEntityController<>(player));
	}
}
