package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

public class EnemyController extends MovementController<Enemy> {

	public final EntityNavigator nav;

	public EnemyController(Enemy enemy) {
		super(enemy);

		nav = new EntityNavigator(enemy, new AStarPathFinder(Game.world().environment().getMap()));
	}

	@Override
	public void update() {
		super.update();
		// maybe add flee range later
		int dist = (int) getEntity().getCenter().distance(getEntity().getTarget().getCenter()),
				visionRange = getEntity().visionRange;
		boolean canHit = getEntity().getAttackAbility().calculateImpactArea()
				.intersects(getEntity().getTarget().getCollisionBox()), canSee = dist <= visionRange;

		if (!getEntity().isDead()) {
			if (canSee) getEntity().setAngle(GeometricUtilities.calcRotationAngleInDegrees(getEntity().getCenter(),
					getEntity().getTarget().getCenter()));

			if (canHit) {
				nav.stop();
			} else {
				if (canSee) {
					nav.navigate(getEntity().getTarget().getCenter());
				}
			}
		}
	}

}
