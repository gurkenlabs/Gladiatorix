package de.litigame.entities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

public class Projectile extends Creature implements IUpdateable {

	private final ICombatEntity executor;
	private final Set<ICombatEntity> hitEntities = new HashSet<>();
	private final List<ProjectileHitListener> hitListeners = new ArrayList<>();

	public Projectile(ICombatEntity executor) {
		this(executor.getLocation(), executor.getAngle(), executor);
	}

	public Projectile(Point2D position, double angle, ICombatEntity executor) {
		this.executor = executor;
		setLocation(position);
		setAngle(angle);

		Game.loop().attach(this);
	}

	@Override
	public void update() {
		setLocation(GeometricUtilities.project(getLocation(), getAngle(), getTickVelocity()));
		for (ICombatEntity hit : Game.world().environment().findCombatEntities(getCollisionBox(),
				entity -> !entity.equals(executor) && !hitEntities.contains(entity))) {
			for (ProjectileHitListener listener : hitListeners) listener.apply(executor, hit, this);
			hitEntities.add(hit);
		}
	}
}
