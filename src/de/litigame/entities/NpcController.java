package de.litigame.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.attributes.AttributeModifier;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.behavior.AStarNode;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;
import de.litigame.GameManager;
import de.litigame.utilities.PathFinderUtilities;

import java.util.Set;

public class NpcController extends MovementController<NPC> {

    private static final double P_REST = 0.7;
    private static final int REST_TIME = 1000;
    private static final int WANDER_RANGE = 2;
    public final EntityNavigator nav;
    private int rest = 0;
    private final AttributeModifier<Float> slowness = new AttributeModifier<>(Modification.DIVIDE, 2);

    public NpcController(NPC npc) {
        super(npc);
        nav = new EntityNavigator(npc, new AStarPathFinder(Game.world().environment().getMap()));
    }
    private void idle() {
        if (Math.random() < P_REST) rest();
        else wanderAround();
    }

    private void rest() {
        rest = GameManager.MillisToTicks(REST_TIME);
    }

    private void turnToTarget() {
        getEntity().setAngle(GeometricUtilities.calcRotationAngleInDegrees(getEntity().getCenter(),
                Player.getInstance().getCenter()));
    }

    @Override
    public void update() {
        super.update();
        // maybe add flee range later
        if (rest > 0) --rest;

        int dist = (int) getEntity().getCenter().distance(Player.getInstance().getCenter()),
                visionRange = getEntity().visionRange;
        boolean canSee = dist <= visionRange, isDead = getEntity().isDead(), hasGoal = nav.isNavigating(),
                rests = rest > 0;

        if (!isDead) {
            if (canSee) {
                if(!Player.getInstance().interactListener.contains(this)) {
                    Player.getInstance().interactListener.add(this);
                }
                turnToTarget();
            } else {
                Player.getInstance().interactListener.remove(this);
                if (!rests && !hasGoal) {
                    idle();
                }
            }
        }
    }

    private void wanderAround() {
        Set<AStarNode> nodes = PathFinderUtilities.getNodesAround(((AStarPathFinder) nav.getPathFinder()).getGrid(),
                getEntity().getCenter(), WANDER_RANGE);

        nav.navigate(Game.random().choose(nodes).getLocation());
    }
}
