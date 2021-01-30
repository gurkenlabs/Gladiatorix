package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.litigame.GameManager;

public interface IInteractEntity extends IEntity {

	default void addToWorld() {
		Game.world().environment().add(this);
		GameManager.interactEntities.add(this);
	}

	void interact(Player player);

	default void removeFromWorld() {
		Game.world().environment().remove(this);
		GameManager.interactEntities.remove(this);
	}
}
