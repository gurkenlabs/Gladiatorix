package de.litigame;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

public class PlayerCamera extends PositionLockCamera implements IUpdateable {

	private static final int TIME_OUT = 10000;
	private static final int TRANSITION_MILLIS = 1000;
	private static final float ZOOM_IN = 0f;
	private static final float ZOOM_OUT = 2f;

	private int lastMoved = 0;
	private boolean zoomedIn = false;

	public PlayerCamera() {
		super(Player.getInstance());
		Player.getInstance().onMoved(e -> moved());
	}

	private void moved() {
		lastMoved = 0;
		if (!zoomedIn) toggleZoom();
	}

	private void toggleZoom() {
		if (zoomedIn) {
			setZoom(ZOOM_OUT, TRANSITION_MILLIS);
			zoomedIn = false;
		} else {
			setZoom(ZOOM_IN, TRANSITION_MILLIS);
			zoomedIn = true;
		}
	}

	@Override
	public void update() {
		++lastMoved;
		if (lastMoved > TIME_OUT && zoomedIn) toggleZoom();
	}
}
