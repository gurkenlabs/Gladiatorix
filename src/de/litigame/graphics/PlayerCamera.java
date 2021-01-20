package de.litigame.graphics;

import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.litigame.entities.Player;

public class PlayerCamera extends PositionLockCamera {

	public static final int STD_DELAY = 500;
	public static final float STD_ZOOM = 2f;

	public PlayerCamera() {
		this(Player.getInstance());
	}

	public PlayerCamera(Player player) {
		super(player);
		setZoom(STD_ZOOM, STD_DELAY);
	}
}
