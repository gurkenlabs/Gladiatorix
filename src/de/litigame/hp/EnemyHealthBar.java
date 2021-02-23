package de.litigame.hp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Enemy;
import de.litigame.entities.Player;

public class EnemyHealthBar implements IRenderable {

	private final Enemy entity;

	public EnemyHealthBar(Enemy enemy) {
		entity = enemy;
	}

	@Override
	public void render(Graphics2D g) {
		int hp = entity.getHitPoints().get();
		double fac = (double) hp / entity.getHitPoints().getMax();
		String image = Math.round(fac * 10) * 10 + "-hp";
		BufferedImage bar = Resources.images().get(image);
		g.drawImage(bar,
				(int) (entity.getX()
						+ (Game.world().environment().getMap().getWidth() * 16 - Player.getInstance().getX())),
				(int) (entity.getY()
						+ (Game.world().environment().getMap().getHeight() * 8 - Player.getInstance().getY())),
				(bar.getWidth()), (bar.getHeight()), null);
	}

}
