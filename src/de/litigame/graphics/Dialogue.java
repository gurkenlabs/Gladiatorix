package de.litigame.graphics;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.litigame.GameManager;

public class Dialogue implements IUpdateable {
	private int displayTime;
	public final int time;
	public final String value;
	public final double x, y;

	public Dialogue(String value, double x, double y, int time) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.time = GameManager.MillisToTicks(time);
		resetTime();
	}

	public void prepare() {
		Game.loop().attach(this);
	}

	public void resetTime() {
		displayTime = time;
	}

	public boolean shouldBeDrawn() {
		return displayTime > 0;
	}

	public void suspend() {
		Game.loop().detach(this);
	}

	@Override
	public void update() {
		if (displayTime > 0) --displayTime;
	}
}
