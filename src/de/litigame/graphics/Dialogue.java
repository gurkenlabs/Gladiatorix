package de.litigame.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;

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
		return displayTime > 0 || messageIndex < messages.length - 1;
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
		int marginY = Game.window().getHeight() - box.getHeight() - 100;

		int paddingX = 50;
		int paddingY = 50;

		Font mc;
		try {
			mc = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Minecraft.ttf")).deriveFont((float) 32);
		} catch (Exception e) {
			mc = null;
			e.printStackTrace();
		}
		FontMetrics fm = g.getFontMetrics(mc);
		StringBuilder sb = new StringBuilder();
		String[] message = getMessage().split(" ");

		int pointer = 0;
		for (int i = 0; i < message.length; i++) {
			if (fm.stringWidth(N_words(getMessage(), pointer, i + 1)) > (box.getWidth() - paddingX)) {
				sb.append("\n" + message[i]);
				pointer += i - pointer;
			} else sb.append(message[i] + " ");
		}

		g.setFont(mc);
		g.setColor(Color.WHITE);
		g.drawImage(box, marginX, marginY, null);
		drawString(g, sb.toString(), marginX + box.getWidth() / 2, marginY + paddingY);
	}

	private String N_words(String s, int start, int n) {
		String[] arr = s.split(" ");
		String nWords = "";
		for (int i = start; i < n; i++) {
			nWords = nWords + " " + arr[i];
		}
		return nWords;
	}

	private void drawString(Graphics g, String text, int x, int y) {
		Font mc;
		try {
			mc = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Minecraft.ttf")).deriveFont((float) 32);
		} catch (Exception e) {
			mc = null;
			e.printStackTrace();
		}

		FontMetrics fm = g.getFontMetrics(mc);
		for (String line : text.split("\n")) g.drawString(line, x - fm.stringWidth(line) / 2, y += g.getFontMetrics().getHeight());
	}
}
