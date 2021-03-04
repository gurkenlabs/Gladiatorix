package de.litigame.graphics;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dialogue implements IUpdateable, IRenderable {
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

	@Override
	public void render(Graphics2D g) {
		BufferedImage box = Resources.images().get("dialogue_box");

		int marginX = 100;
		int marginY = Game.window().getHeight()-box.getHeight()-100;

		int paddingX = 50;
		int paddingY = 150;

		Font mc;
		try {
			mc = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Minecraft.ttf")).deriveFont((float) 40);
		} catch (Exception e) {
			mc = null;
			e.printStackTrace();
		}
		g.setFont(mc);
		g.setColor(Color.WHITE);
		g.drawImage(box,marginX, marginY,null);
		g.drawString(getMessage(), marginX+paddingX, marginY+paddingY);
	}
}
