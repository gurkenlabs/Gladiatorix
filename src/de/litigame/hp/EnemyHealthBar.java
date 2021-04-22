package de.litigame.hp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Enemy;

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
		BufferedImage bar = Resources.images().get(image+".png");
		g.drawImage(bar, (int) (entity.getCenter().getX() - Game.world().camera().getFocus().getX() + 299.5 / Game.world().camera().getZoom()), (int) (entity.getCenter().getY() - Game.world().camera().getFocus().getY() + 143 / Game.world().camera().getZoom()), (bar.getWidth()), (bar.getHeight()), null);
	}

}
