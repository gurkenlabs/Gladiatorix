package de.litigame.graphics;

import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.litigame.entities.Player;

public class PlayerCamera extends PositionLockCamera {

	public static final int STD_DURATION = 500;
	public static final float STD_ZOOM = 1.5f;
	public static final int TIME_OUT = 200;
	public static final float ZOOM_IN = 2.5f;

	public PlayerCamera() {
		this(Player.getInstance());
	}

	public PlayerCamera(Player player) {
		super(player);
	}
}
