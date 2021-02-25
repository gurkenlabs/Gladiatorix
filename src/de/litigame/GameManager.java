package de.litigame;

import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.CombatEntity;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.environment.CreatureMapObjectLoader;
import de.gurkenlabs.litiengine.environment.Environment;
import de.litigame.entities.Enemy;
import de.litigame.entities.IInteractEntity;
import de.litigame.entities.ItemProp;
import de.litigame.entities.Player;
import de.litigame.entities.Villager;
import de.litigame.graphics.PlayerCamera;
import de.litigame.items.Item;
import de.litigame.items.Items;
import de.litigame.shop.Shops;
import de.litigame.spawning.Spawnpoints;

public class GameManager {

	public static final Set<IInteractEntity> interactEntities = new HashSet<>();

	public static void enterPortal(String map, double x, double y) {
		Game.world().environment().remove(Player.getInstance());
		switchToMap(map);
		Player.getInstance().setLocation(x, y);
		Game.world().environment().add(Player.getInstance());
	}

	public static void init() {
		CreatureMapObjectLoader.registerCustomCreatureType(Enemy.class);
		CreatureMapObjectLoader.registerCustomCreatureType(Villager.class);

		Game.world().setCamera(new PlayerCamera());

		Game.world().onLoaded(GameManager::setupMapObjects);

		switchToMap("map1");
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());

		Player.getInstance().hotbar.addItem(Items.getItem("bow"));
		Player.getInstance().hotbar.addItem(Items.getItem("sword"));

		switchToState(GameState.INGAME);
	}

	public static int MillisToTicks(int millis) {
		return Game.loop().getTickRate() * millis / 1000;
	}

	public static void removeItemEntities(Item item) {
		for (Prop entity : Game.world().environment().getProps()) {
			if (entity instanceof ItemProp && ((ItemProp) entity).item.getName().equals(item.getName()))
				Game.world().environment().remove(entity);
		}
	}

	private static void setupMapObjects(Environment env) {
		setupTriggers(env);
		setupSpawnpoints(env);
	}

	public static void setupSpawnpoints(Environment env) {
		for (final CollisionBox infoBox : env.getCollisionBoxes()) {
			if (infoBox.hasTag("enemyspawndata")) {
				int waveCount = infoBox.getProperties().getIntValue("waveCount");
				int waveDelay = infoBox.getProperties().getIntValue("waveDelay");
				Spawnpoints.createSpawnpoints(env.getSpawnPoints().stream().filter(spawn -> spawn.hasTag("enemyspawn"))
						.collect(Collectors.toList()), waveCount, waveDelay);
				return;
			}
		}

	}

	private static void setupTriggers(Environment env) {
		for (final Trigger trigger : env.getTriggers()) {
			if (trigger.hasTag("deadly")) {
				trigger.addActivatedListener(e -> {
					IEntity entity = e.getEntity();
					if (entity instanceof CombatEntity) ((CombatEntity) entity).die();
				});
			}
			if (trigger.hasTag("wavestart")) {
				trigger.addActivatedListener(e -> {
					Spawnpoints.spawnNextWave();
					Game.world().environment().remove(trigger);
				});
			}
			if (trigger.hasTag("portal")) {
				trigger.addActivatedListener(e -> {
					if (e.getEntity() instanceof Player) {
						String map = trigger.getProperties().getStringValue("toMap");
						String[] coords = trigger.getProperties().getStringValue("toPos").split(",");
						enterPortal(map, Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()));
					}
				});
			}
			if (trigger.hasTag("zoom")) {
				trigger.addActivatedListener(e -> {
					if (e.getEntity() instanceof Player) {
						float zoom = trigger.getProperties().getFloatValue("zoomValue");
						int duration = trigger.getProperties().hasCustomProperty("zoomDuration")
								? trigger.getProperties().getIntValue("zoomDuration")
								: PlayerCamera.STD_DELAY;
						Game.world().camera().setZoom(zoom, duration);
					}
				});
				trigger.addDeactivatedListener(e -> {
					if (e.getEntity() instanceof Player) {
						float zoom = PlayerCamera.STD_ZOOM;
						int duration = trigger.getProperties().hasCustomProperty("zoomDuration")
								? trigger.getProperties().getIntValue("zoomDuration")
								: PlayerCamera.STD_DELAY;
						Game.world().camera().setZoom(zoom, duration);
					}
				});
			}
			if (trigger.hasTag("shop")) trigger.addActivatedListener(e -> {
				IEntity entity = e.getEntity();
				if (entity instanceof Player) {
					String shop = trigger.getProperties().getStringValue("shopName");
					entity.detachControllers();
					Shops.getShop(shop).open(() -> {
						entity.attachControllers();
					});
				}
			});
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
