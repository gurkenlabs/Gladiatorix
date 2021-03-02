package de.litigame.utilities;

import java.awt.geom.Point2D;
import java.util.Collection;

import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.util.geom.Vector2D;

public class GeometryUtilities {

	public static Point2D getCenterLocation(IEntity entity) {
		Vector2D loc = new Vector2D(entity.getLocation().getX(), entity.getLocation().getY());
		Vector2D delta = new Vector2D(entity.getCenter(), entity.getLocation());
		Vector2D start = loc.add(delta);
		return new Point2D.Double(start.getX(), start.getY());
	}

	public static <T extends IEntity> T getNearestEntity(IEntity center, Collection<T> entities) {
		T nearest = null;
		int minDist = Integer.MAX_VALUE;
		for (T entity : entities) {
			int dist = (int) center.getCenter().distance(entity.getCenter());
			if (dist < minDist) {
				nearest = entity;
				minDist = dist;
			}
		}
		return nearest;
	}

	public static void setCenter(IEntity entity, Point2D center) {
		entity.setLocation(center);
		entity.setLocation(getCenterLocation(entity));
	}

}
