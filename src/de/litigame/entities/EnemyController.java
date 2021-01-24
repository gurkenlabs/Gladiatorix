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

	private void attack() {
		nav.stop();

	}

	private void chase() {
		nav.navigate(getEntity().getTarget().getCenter());
	}

	private void idle() {

	}

	private void slowDown() {

	}

	private void turnToTarget() {
		getEntity().setAngle(GeometricUtilities.calcRotationAngleInDegrees(getEntity().getCenter(),
				getEntity().getTarget().getCenter()));
	}

	@Override
	public void update() {
		super.update();
		// maybe add flee range later
		int dist = (int) getEntity().getCenter().distance(getEntity().getTarget().getCenter()),
				visionRange = getEntity().visionRange;
		boolean canHit = getEntity().getAttackAbility().calculateImpactArea()
				.intersects(getEntity().getTarget().getCollisionBox()), canSee = dist <= visionRange,
				isDead = getEntity().isDead(), hasGoal = nav.isNavigating();

		if (!isDead) {
			if (canSee) {
				turnToTarget();
				if (canHit) {
					attack();
				} else {
					chase();
				}
			} else {
				if (!hasGoal) {
					slowDown();
				} else {
					idle();
				}
			}
		}
	}
}
