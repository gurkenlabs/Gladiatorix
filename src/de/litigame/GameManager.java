package de.litigame;

import java.util.HashSet;
import java.util.Set;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CombatEntity;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.environment.CreatureMapObjectLoader;
import de.gurkenlabs.litiengine.environment.Environment;
import de.litigame.entities.Enemy;
import de.litigame.entities.IInteractEntity;
import de.litigame.entities.Player;
import de.litigame.entities.Villager;
import de.litigame.graphics.PlayerCamera;
import de.litigame.items.Items;
import de.litigame.shop.Shops;

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

	private static void setupMapObjects(Environment env) {
		setupTriggers(env);
	}

	private static void setupTriggers(Environment env) {
		for (Trigger trigger : env.getTriggers()) {
			if (trigger.hasTag("deadly")) trigger.addActivatedListener(e -> {
				IEntity entity = e.getEntity();
				if (entity instanceof CombatEntity) ((CombatEntity) entity).die();
			});
			if (trigger.hasTag("portal")) trigger.addActivatedListener(e -> {
				if (e.getEntity() instanceof Player) {
					String map = trigger.getProperties().getStringValue("toMap");
					String[] coords = trigger.getProperties().getStringValue("toPos").split(",");
					enterPortal(map, Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()));
				}
			});
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
					String shop = trigger.getProperties().getStringValue("shop_name");
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
