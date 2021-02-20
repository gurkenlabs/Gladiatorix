package de.litigame;

import java.util.stream.Collectors;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.CombatEntity;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.environment.CreatureMapObjectLoader;
import de.gurkenlabs.litiengine.environment.Environment;
import de.litigame.entities.Enemy;
import de.litigame.entities.Player;
import de.litigame.graphics.PlayerCamera;
import de.litigame.items.Items;

public class GameManager {

	public static void enterPortal(String map, double x, double y) {
		switchToMap(map);
		Player.getInstance().setLocation(x, y);
		Game.world().environment().add(Player.getInstance());
	}

	public static void init() {
		CreatureMapObjectLoader.registerCustomCreatureType(Enemy.class);

		Game.world().setCamera(new PlayerCamera());

		Game.world().onLoaded(GameManager::setupMapObjects);

		switchToMap("map1");
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());

		Player.getInstance().hotbar.addItem(Items.getItem("bow"));
		Player.getInstance().hotbar.addItem(Items.getItem("sword"));

		switchToState(GameState.INGAME);
	}

	private static void setupMapObjects(Environment env) {
		setupTriggers(env);
		setupSpawnpoints(env);
	}

	private static void setupTriggers(Environment env) {
		for (final Trigger trigger : env.getTriggers()) {
			if (trigger.hasTag("deadly")) {
				trigger.addActivatedListener(e -> {
					final IEntity entity = e.getEntity();
					if (entity instanceof CombatEntity) {
						((CombatEntity) entity).die();
					}
				});
			}
			if (trigger.hasTag("wavestart")) {
				trigger.addActivatedListener(e -> {
					Spawnpoints.spawnWave(0);
					Game.world().environment().remove(trigger);
				});
			}
			if (trigger.hasTag("portal")) {
				trigger.addActivatedListener(e -> {
					if (e.getEntity() instanceof Player) {
						final String map = trigger.getProperties().getStringValue("toMap");
						final String[] coords = trigger.getProperties().getStringValue("toPos").split(",");
						enterPortal(map, Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()));
					}
				});
			}
			if (trigger.hasTag("zoom")) {
				trigger.addActivatedListener(e -> {
					if (e.getEntity() instanceof Player) {
						final float zoom = trigger.getProperties().getFloatValue("zoomValue");
						final int duration = trigger.getProperties().hasCustomProperty("zoomDuration")
								? trigger.getProperties().getIntValue("zoomDuration")
								: PlayerCamera.STD_DELAY;
						Game.world().camera().setZoom(zoom, duration);
					}
				});
				trigger.addDeactivatedListener(e -> {
					if (e.getEntity() instanceof Player) {
						final float zoom = PlayerCamera.STD_ZOOM;
						final int duration = trigger.getProperties().hasCustomProperty("zoomDuration")
								? trigger.getProperties().getIntValue("zoomDuration")
								: PlayerCamera.STD_DELAY;
						Game.world().camera().setZoom(zoom, duration);
					}
				});
			}
		}
	}

	public static void setupSpawnpoints(Environment env) {
		for (final CollisionBox boxbox : env.getCollisionBoxes()) {
			if (boxbox.hasTag("enemyspawndata")) {
				final int waveCount = boxbox.getProperties().getIntValue("waveCount");
				Spawnpoints.createSpawnpoints(env.getSpawnPoints().stream().filter(spawn -> spawn.hasTag("enemyspawn"))
						.collect(Collectors.toList()), waveCount);
				return;
			}
		}

	}

	public static void switchToMap(String map) {
		Game.world().unloadEnvironment();
		Game.world().loadEnvironment(map);
		Game.world().camera().setZoom(PlayerCamera.STD_ZOOM, PlayerCamera.STD_DELAY);
	}

	public static void switchToState(GameState state) {
	}
}
