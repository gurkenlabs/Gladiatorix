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
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.gurkenlabs.litiengine.util.geom.Vector2D;

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
		setLocation(calcStartLocation(position));
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

	private Point2D calcStartLocation(Point2D position) {
		Vector2D loc = new Vector2D(getLocation().getX(), getLocation().getY());
		Vector2D delta = new Vector2D(getCenter(), position);
		Vector2D start = loc.add(delta);
		return new Point2D.Double(start.getX(), start.getY());
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
		Game.physics().move(this, getTickVelocity());

		for (ICollisionEntity hit : Game.physics().getCollisionEntities()) {
			if (getCollisionBox().intersects(hit.getCollisionBox())) {
				if (hit.getCollisionType() == Collision.STATIC) {
					fall();
					return;
				} else if (hit instanceof ICombatEntity) {
					ICombatEntity combatEntity = (ICombatEntity) hit;
					if (!combatEntity.equals(executor) && !hitEntities.contains(combatEntity)
							&& !combatEntity.isDead()) {
						for (ProjectileHitListener listener : hitListeners)
							listener.apply(executor, combatEntity, this);
						if (!multiTarget) {
							fall();
							return;
						}
						hitEntities.add(combatEntity);
					}
				}
			}
		}
		if (origin.distance(getCenter()) >= range) fall();
	}
}
