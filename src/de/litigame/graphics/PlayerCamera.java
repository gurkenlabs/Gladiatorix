package de.litigame.graphics;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.litigame.entities.Player;

public class PlayerCamera extends PositionLockCamera {

	private static final int TIME_OUT = 200;
	private static final int TRANSITION_MILLIS = 500;
	private static final float ZOOM_IN = 2.5f;
	private static final float ZOOM_OUT = 1.5f;

	private static int millisToLoopValue(int millis) {
		return millis * Game.loop().getTickRate() / 1000;
	}

	private int lastMoved = 0;

	private boolean zoomedIn;

	public PlayerCamera() {
		this(Player.getInstance());
	}

	public PlayerCamera(Player player) {
		super(player);
		player.onMoved(e -> moved());

		init();
	}

	private void init() {
		setZoom(ZOOM_IN, 0);
		zoomedIn = true;
		setZoom(false);
	}

	private void moved() {
		lastMoved = 0;
		setZoom(true);
	}

	private void setZoom(boolean zoomIn) {
		if (zoomIn == zoomedIn) return;

		else if (zoomedIn) {
			setZoom(ZOOM_OUT, TRANSITION_MILLIS);
			zoomedIn = false;
		} else {
			setZoom(ZOOM_IN, TRANSITION_MILLIS);
			zoomedIn = true;
		}
	}

	@Override
	public void update() {
		super.update();
		++lastMoved;
		if (lastMoved > millisToLoopValue(TIME_OUT)) setZoom(false);
	}
}
