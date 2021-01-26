package de.litigame.utilities;

import java.awt.geom.Point2D;

import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.util.geom.Vector2D;

public class GeometryUtilities {

	public static Point2D getCenterLocation(IEntity entity) {
		Vector2D loc = new Vector2D(entity.getLocation().getX(), entity.getLocation().getY());
		Vector2D delta = new Vector2D(entity.getCenter(), entity.getLocation());
		Vector2D start = loc.add(delta);
		return new Point2D.Double(start.getX(), start.getY());
	}

}
