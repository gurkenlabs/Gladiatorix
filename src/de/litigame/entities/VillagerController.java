package de.litigame.entities;

import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.behavior.AStarNode;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.litigame.GameManager;
import de.litigame.utilities.PathFinderUtilities;

public class VillagerController extends MovementController<Villager> {

	private static final double P_REST = 0.7;
	private static final int REST_TIME = 1000;
	private static final int WANDER_RANGE = 2;
	public final EntityNavigator nav;
	private int rest = 0;

	public VillagerController(Villager villager) {
		super(villager);
		nav = new EntityNavigator(villager, new AStarPathFinder(Game.world().environment().getMap()));
	}

	private void idle() {
		if (Math.random() < P_REST) rest();
		else wanderAround();
	}

	private void rest() {
		rest = GameManager.MillisToTicks(REST_TIME);
	}

	@Override
	public void update() {
		super.update();
		if (rest > 0) --rest;

		boolean isDead = getEntity().isDead(), hasGoal = nav.isNavigating(), rests = rest > 0;

		if (!isDead && !hasGoal && !rests) {
			idle();
		}
	}

	private void wanderAround() {
		Set<AStarNode> nodes = PathFinderUtilities.getNodesAround(((AStarPathFinder) nav.getPathFinder()).getGrid(),
				getEntity().getCenter(), WANDER_RANGE);

		nav.navigate(Game.random().choose(nodes).getLocation());
	}
}
