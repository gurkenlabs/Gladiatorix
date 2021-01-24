package de.litigame.entities;

import java.util.HashSet;
import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.attributes.Attribute;
import de.gurkenlabs.litiengine.attributes.AttributeModifier;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.behavior.AStarGrid;
import de.gurkenlabs.litiengine.entities.behavior.AStarNode;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

public class EnemyController extends MovementController<Enemy> {

	private static final int REST_TIME = 1000;
	private static final int WANDER_RANGE = 3;
	public final EntityNavigator nav;
	private int rest = 0;
	private final AttributeModifier<Float> slowness = new AttributeModifier<>(Modification.DIVIDE, 2);

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
		if (Game.random().nextBoolean()) rest();
		else wanderAround();
	}

	private void rest() {
		rest = REST_TIME * Game.loop().getTickRate() / 1000;
	}

	private void slowDown() {
		Attribute<Float> attribute = getEntity().getVelocity();
		if (!attribute.isModifierApplied(slowness)) attribute.addModifier(slowness);
	}

	private void speedUp() {
		Attribute<Float> attribute = getEntity().getVelocity();
		if (attribute.isModifierApplied(slowness)) attribute.removeModifier(slowness);
	}

	private void turnToTarget() {
		getEntity().setAngle(GeometricUtilities.calcRotationAngleInDegrees(getEntity().getCenter(),
				getEntity().getTarget().getCenter()));
	}

	@Override
	public void update() {
		super.update();
		// maybe add flee range later
		if (rest > 0) --rest;

		int dist = (int) getEntity().getCenter().distance(getEntity().getTarget().getCenter()),
				visionRange = getEntity().visionRange;
		boolean canHit = getEntity().getAttackAbility().calculateImpactArea()
				.intersects(getEntity().getTarget().getCollisionBox()), canSee = dist <= visionRange,
				isDead = getEntity().isDead(), hasGoal = nav.isNavigating(), rests = rest > 0;

		if (!isDead) {
			if (canSee) {
				speedUp();
				turnToTarget();
				if (canHit) {
					attack();
				} else {
					chase();
				}
			} else {
				slowDown();
				if (!rests && !hasGoal) {
					idle();
				}
			}
		}
	}

	private void wanderAround() {
		AStarGrid grid = ((AStarPathFinder) nav.getPathFinder()).getGrid();

		Set<AStarNode> nodesInRange = new HashSet<>();
		nodesInRange.add(grid.getNode(getEntity().getCenter()));
		for (int i = 0; i < WANDER_RANGE; ++i) {
			Set<AStarNode> discoveredNodes = new HashSet<>();
			for (AStarNode node : nodesInRange) {
				discoveredNodes.addAll(grid.getNeighbors(node));
			}
			nodesInRange.addAll(discoveredNodes);
		}

		nav.navigate(Game.random().choose(nodesInRange).getLocation());
	}
}
