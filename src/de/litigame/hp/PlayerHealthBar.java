package de.litigame.hp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Player;

public class PlayerHealthBar implements IRenderable {

	private final Player player;

	public PlayerHealthBar(Player player) {
		this.player = player;
	}

	@Override

	public void render(Graphics2D g) {
		BufferedImage border = Resources.images().get("hp-border");
		BufferedImage fill = Resources.images().get("hp-fill");
		double hp = player.getHitPoints().get();
		double frac = hp / player.getHitPoints().getMax();
		int scaling = 5;
		g.drawImage(fill,
				// Game.window().getResolution().width/2,
				(Game.window().getResolution().width - fill.getWidth() * scaling) - 10,
				// Game.window().getResolution().height - fill.getHeight()*scaling - 100,
				10, (int) (border.getWidth() * scaling * frac), fill.getHeight() * scaling, null);
		g.drawImage(border,
				// Game.window().getResolution().width/2,
				(Game.window().getResolution().width - border.getWidth() * scaling) - 10,
				// Game.window().getResolution().height - border.getHeight()*scaling-100,
				10, border.getWidth() * scaling, border.getHeight() * scaling, null);
		// Player.getInstance().hotbar.render(g);
	}

}
