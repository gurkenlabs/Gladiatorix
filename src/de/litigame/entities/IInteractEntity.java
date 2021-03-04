package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.EntityListener;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.environment.Environment;
import de.litigame.GameManager;

public interface IInteractEntity extends IEntity {

	final class InteractEntityListener implements EntityListener {
		@Override
		public void loaded(IEntity entity, Environment environment) {
			GameManager.interactEntities.add((IInteractEntity) entity);
		}

		@Override
		public void removed(IEntity entity, Environment environment) {
			GameManager.interactEntities.remove(entity);
		}
	}

	void interact(Player player);
}
