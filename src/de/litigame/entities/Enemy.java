package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.EnvironmentLoadedListener;

@AnimationInfo(spritePrefix = "test")
@CollisionInfo(collision = true, collisionBoxWidth = 4, collisionBoxHeight = 4, valign = Valign.MIDDLE)
@EntityInfo(width = 4, height = 4)
@MovementInfo(velocity = 50)

public class Enemy extends Creature implements EnvironmentLoadedListener {

	public EntityNavigator nav;

	public Enemy() {
		super("enemy");
		Game.world().onLoaded(this);
	}

	private void init() {
		nav = new EntityNavigator(this, new AStarPathFinder(Game.world().environment().getMap())) {
			@Override
			public void update() {
				super.update();

				navigate(Player.getInstance().getCenter());
			}
		};
	}

	@Override
	public void loaded(Environment environment) {
		init();
		Game.world().removeLoadedListener(this);
	}
}
