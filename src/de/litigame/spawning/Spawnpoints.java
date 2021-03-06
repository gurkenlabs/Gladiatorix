package de.litigame.spawning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.litigame.entities.Enemy;
import de.litigame.items.Items;
import de.litigame.items.Weapon;

public class Spawnpoints {

	private static int currentWave, waveCount;
	private static List<EnemySpawnpoint> spawns = new ArrayList<>();

	public static boolean allDead() {
		for (final Creature c : Game.world().environment().getCreatures()) {
			if (c instanceof Enemy && !c.isDead()) {
				return false;
			}
		}
		return true;
	}

	public static void createSpawnpoints(Collection<Spawnpoint> collection, int waveCount, int waveDelay) {
		currentWave = 0;
		Spawnpoints.waveCount = waveCount;
		spawns.clear();
		collection.forEach(e -> {
			final EnemySpawnpoint spawn = new EnemySpawnpoint(waveDelay, e);
			for (int i = 0; i < waveCount; i++) {
				final List<Enemy> wave = new ArrayList<>();
				if (spawn.spawnpoint.getProperties().hasCustomProperty("wave_" + (i + 1))) {
					for (final String enemy : spawn.spawnpoint.getProperties().getStringValue("wave_" + (i + 1))
							.split(",")) {
						switch (Integer.valueOf(enemy)) {
						case 1:
							wave.add(new Enemy("enemy1", (Weapon) Items.getItem("Trainingsschwert"), 1, 100, 70000, 2));
							break;
						case 2:
							wave.add(new Enemy("enemy2", (Weapon) Items.getItem("Steinaxt"), 2, 350, 70000, 4));
							break;
						case 3:
							wave.add(new Enemy("enemy3", (Weapon) Items.getItem("Eisenschwert"), 4, 750, 70000, 5));
							break;
						}
					}
				}
				spawn.addWave(wave);
			}
			spawns.add(spawn);
		});
	}

	public static boolean isOver() {
		return currentWave >= waveCount && allDead();
	}

	public static void spawnNextWave() {
		for (final EnemySpawnpoint spawn : spawns) {
			spawn.spawnWave(currentWave);
		}
		++currentWave;
	}
}
