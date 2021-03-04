package de.litigame.graphics;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.litigame.GameManager;

public class Dialogue implements IUpdateable {
	private int displayTime, messageIndex;
	private final String[] messages;
	private final int time;
	public final double x, y;

	public Dialogue(String[] messages, double x, double y, int time) {
		this.messages = messages;
		this.x = x;
		this.y = y;
		this.time = GameManager.MillisToTicks(time);
		reset();
	}

	public String getMessage() {
		return messages[messageIndex];
	}

	public void prepare() {
		Game.loop().attach(this);
	}

	public void reset() {
		displayTime = time;
		messageIndex = 0;
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
		else if (messageIndex < messages.length - 1) {
			++messageIndex;
			displayTime = time;
		}
	}
}
