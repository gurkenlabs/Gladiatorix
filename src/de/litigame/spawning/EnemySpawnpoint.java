package de.litigame.spawning;

import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.litigame.entities.Enemy;

public class EnemySpawnpoint {

	private final int delay;

	public final Spawnpoint spawnpoint;

	private final List<List<Enemy>> waves = new ArrayList<>();

	public EnemySpawnpoint(int delay, Spawnpoint spawnpoint) {
		this.delay = delay;
		this.spawnpoint = spawnpoint;
	}

	public void addWave(List<Enemy> wave) {
		waves.add(wave);
	}

	public void spawnWave(int wave) {
		spawnWave(wave, 0);
	}

	public void spawnWave(int wave, int enemy) {
		if (waves.get(wave).size() <= enemy) return;
		spawnpoint.spawn(new Enemy(waves.get(wave).get(enemy)));
		Game.loop().perform(delay, () -> spawnWave(wave, enemy + 1));
	}
}
