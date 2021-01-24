package de.litigame.abilities;

import de.gurkenlabs.litiengine.entities.ICollisionEntity;

public interface IHitAbility {
	boolean canHit(ICollisionEntity target);
}
