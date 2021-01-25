package de.litigame.utilities;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import de.gurkenlabs.litiengine.entities.behavior.AStarGrid;
import de.gurkenlabs.litiengine.entities.behavior.AStarNode;

public class PathFinderUtilities {

	public static AStarNode getFurthestNode(AStarGrid grid, Point2D center, Point2D start, int range) {
		AStarNode furthest = grid.getNode(start);
		double maxDist = center.distance(start);

		for (AStarNode node : getNodesAround(grid, start, range)) {
			double dist = center.distance(node.getLocation());
			if (dist < maxDist) {
				furthest = node;
				maxDist = dist;
			}
		}

		return furthest;
	}

	public static Set<AStarNode> getNodesAround(AStarGrid grid, Point2D start, int range) {
		Set<AStarNode> nodesInRange = new HashSet<>();

		nodesInRange.add(grid.getNode(start));
		for (int i = 0; i < range; ++i) {
			Set<AStarNode> discoveredNodes = new HashSet<>();
			for (AStarNode node : nodesInRange) {
				discoveredNodes.addAll(grid.getNeighbors(node));
			}
			nodesInRange.addAll(discoveredNodes);
		}

		return nodesInRange;
	}
}
