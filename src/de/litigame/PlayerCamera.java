package de.litigame;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.litigame.entities.Player;

public class PlayerCamera extends PositionLockCamera implements IUpdateable {

	private static final int TIME_OUT = 10000;
	private static final int TRANSITION_MILLIS = 1000;
	private static final float ZOOM_IN = 2f;
	private static final float ZOOM_OUT = 1.5f;

	private int lastMoved = 0;
	private boolean zoomedIn;

	public PlayerCamera() {
		super(Player.getInstance());
		Player.getInstance().onMoved(e -> moved());

		zoomedIn = true;
		toggleZoom();
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
		super.update();
		++lastMoved;
		if (lastMoved > TIME_OUT && zoomedIn) toggleZoom();
	}
}
