package de.litigame;

import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;

@AnimationInfo(spritePrefix = "player")
public class Player extends Creature {

	private static Player instance = new Player();

	private Player() {
		super("player");
		addController(new KeyboardEntityController<>(this));
	}

	public static Player getInstance() {
		return instance;
	}
}
