package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.ICombatEntity;

public interface ProjectileHitListener {
	void apply(ICombatEntity executor, ICombatEntity target, Projectile projectile);
}
