package de.litigame.entities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

@CollisionInfo(collision = false, collisionBoxWidth = 1, collisionBoxHeight = 1, valign = Valign.MIDDLE)
@EntityInfo(width = 1, height = 1)

public class Projectile extends Creature implements IUpdateable, IFighter {

	public interface ProjectileFallListener {
		void apply(ICombatEntity executor, Projectile projectile);
	}

	public interface ProjectileHitListener {
		void apply(ICombatEntity executor, ICombatEntity target, Projectile projectile);
	}

	private final double damage;
	private final ICombatEntity executor;
	private final List<ProjectileFallListener> fallListeners = new ArrayList<>();
	private final Set<ICombatEntity> hitEntities = new HashSet<>();
	private final List<ProjectileHitListener> hitListeners = new ArrayList<>();
	private final boolean multiTarget;

	private final Point2D origin;

	private final int range;

	public Projectile(ICombatEntity executor, Ability ability) {
		this(executor.getCenter(), executor.getAngle(), executor, ability);
	}

	public Projectile(Point2D position, double angle, ICombatEntity executor, Ability ability) {
		damage = ((IFighter) executor).getStrength() * ability.getAttributes().value().get();
		this.executor = executor;
		multiTarget = ability.isMultiTarget();
		origin = (Point2D) position.clone();
		range = ability.getAttributes().range().get();
		setLocation(position);
		setAngle(angle);
		setVelocity(ability.getAttributes().duration().get());

		Game.loop().attach(this);
	}

	public void addFallListener(ProjectileFallListener listener) {
		fallListeners.add(listener);
	}

	public void addHitListener(ProjectileHitListener listener) {
		hitListeners.add(listener);
	}

	private void fall() {
		for (ProjectileFallListener listener : fallListeners) listener.apply(executor, this);
		Game.loop().detach(this);
		Game.world().environment().remove(this);
	}

	@Override
	public double getStrength() {
		return damage;
	}

	public void removeFallListener(ProjectileFallListener listener) {
		fallListeners.remove(listener);
	}

	public void removeHitListener(ProjectileHitListener listener) {
		hitListeners.remove(listener);
	}

	@Override
	public void update() {
		setLocation(GeometricUtilities.project(getLocation(), getAngle(), getTickVelocity()));

		for (ICombatEntity hit : Game.world().environment().findCombatEntities(getCollisionBox(),
				entity -> !entity.equals(executor) && !hitEntities.contains(entity) && !entity.isDead())) {
			for (ProjectileHitListener listener : hitListeners) listener.apply(executor, hit, this);
			hitEntities.add(hit);
			if (!multiTarget) {
				fall();
				return;
			}
		}

		if (origin.distance(getLocation()) >= range) fall();
	}
}
