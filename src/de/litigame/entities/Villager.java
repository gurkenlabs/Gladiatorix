package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.litigame.StaticEnvironmentLoadedListener;

@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 16, valign = Valign.MIDDLE)
@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 20)

public class Villager extends Creature implements IInteractEntity {

	public Villager() {
		super("villager");
		addListener(new InteractEntityListener());

		StaticEnvironmentLoadedListener.attach(e -> {
			VillagerController controller = new VillagerController(this);
			addController(controller);
			Game.loop().attach(controller);
		});
	}

	@Override
	public void interact(Player player) {
		System.out.println("villager interaction");
	}
}
