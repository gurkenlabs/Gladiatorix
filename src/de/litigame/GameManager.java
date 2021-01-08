package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CombatEntity;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.environment.Environment;
import de.litigame.entities.Player;
import de.litigame.graphics.PlayerCamera;
import de.litigame.input.InputManager;

public class GameManager {

	public static void enterPortal(IEntity portal) {
		switchToMap(portal.getProperties().getStringValue("toMap"));

		String[] coords = portal.getProperties().getStringValue("toPos").split(",");
		Player.getInstance().setLocation(Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()));
		Game.world().environment().add(Player.getInstance());
	}

	public static void init() {
		InputManager.init();

		Game.world().setCamera(new PlayerCamera());

		Game.world().onLoaded(GameManager::setupMapObjects);

		switchToMap("map2");
		Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());

		switchToState(GameState.INGAME);
	}

	private static void setupMapObjects(Environment env) {
		for (Trigger trigger : env.getTriggers()) {
			if (trigger.hasTag("deadly")) trigger.addActivatedListener(e -> {
				IEntity entity = e.getEntity();
				if (entity instanceof CombatEntity) ((CombatEntity) entity).die();
			});
			if (trigger.hasTag("portal")) trigger.addActivatedListener(e -> {
				IEntity entity = e.getEntity();
				if (entity instanceof Player) enterPortal(trigger);
			});
		}
	}

	public static void switchToMap(String map) {
		Game.world().unloadEnvironment();
		Game.world().loadEnvironment(map);
	}

	public static void switchToState(GameState state) {
		InputManager.adjustInput(state);
	}
}
