package de.litigame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.litigame.entities.Enemy;

public class Spawnpoints {
	private static List<EnemySpawnpoint> spawns = new ArrayList<>();

	public static void createSpawnpoints(Collection<Spawnpoint> collection, int waveCount) {
		spawns.clear();
		collection.forEach(e -> {
			final EnemySpawnpoint spawn = new EnemySpawnpoint(100, e);
			for (int i = 0; i < waveCount; i++) {
				final List<Enemy> wave = new ArrayList<>();
				if (spawn.spawnpoint.getProperties().hasCustomProperty("wave_" + (i + 1))) {
					for (final String enemy : spawn.spawnpoint.getProperties().getStringValue("wave_" + (i + 1))
							.split(",")) {
						switch (Integer.valueOf(enemy)) {
						case 1:
							wave.add(new Enemy());
							break;
						}
					}
				}
				spawn.addWave(wave);
			}
			spawns.add(spawn);
		});
	}

	public static void spawnWave(int wave) {
		for (final EnemySpawnpoint spawn : spawns) {
			spawn.spawnWave(wave);
		}
	}
}
