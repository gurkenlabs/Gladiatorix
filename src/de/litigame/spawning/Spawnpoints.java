package de.litigame.spawning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.litigame.entities.Enemy;
import de.litigame.spawning.EnemySpawnpoint;

public class Spawnpoints {

	private static int currentWave;
	private static List<EnemySpawnpoint> spawns = new ArrayList<>();

	private static boolean allDead() {
		for (Creature c : Game.world().environment().getCreatures())
			if (c instanceof Enemy && !c.isDead()) return false;
		return true;
	}

	public static void createSpawnpoints(Collection<Spawnpoint> collection, int waveCount, int waveDelay) {
		currentWave = 0;
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
							wave.add(new Enemy("enemy1"));
							break;
						}
					}
				}
				for (Enemy enemy : wave) enemy.onDeath(entity -> {
					if (allDead()) {
						spawnNextWave();
					}
				});
				spawn.addWave(wave);
			}
			spawns.add(spawn);
		});
	}

	public static void spawnNextWave() {
		for (final EnemySpawnpoint spawn : spawns) {
			spawn.spawnWave(currentWave);
		}
		++currentWave;
	}
}
