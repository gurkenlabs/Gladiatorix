package de.litigame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.CombatEntity;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.environment.CreatureMapObjectLoader;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.gui.Appearance;
import de.gurkenlabs.litiengine.gui.GuiProperties;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Enemy;
import de.litigame.entities.IInteractEntity;
import de.litigame.entities.ItemProp;
import de.litigame.entities.Player;
import de.litigame.entities.Villager;
import de.litigame.graphics.Dialogue;
import de.litigame.graphics.PlayerCamera;
import de.litigame.gui.IngameScreen;
import de.litigame.items.Item;
import de.litigame.shop.Shops;
import de.litigame.spawning.Spawnpoints;

public class GameManager {
  public static Font minecraft = Resources.fonts().get("Minecraft.ttf");

  public static final Set<IInteractEntity> interactEntities = new HashSet<>();
  public static String ingame = "ingame.wav";
  public static Point2D spawn;
  public static String save = "savegame";

  public static void enterPortal(String map, double x, double y) {
    if (map.equals(Game.world().environment().getMap().getName())) {
      return;
    }
    Game.world().environment().remove(Player.getInstance());
    switchToMap(map);
    Player.getInstance().setLocation(x, y);
    ingame = "fight.wav";
    Game.audio().playMusic(Resources.sounds().get("fight.wav"));
    Game.world().environment().add(Player.getInstance());
    if (isArena(Game.world().environment())) {
      Spawnpoints.spawnNextWave();
    }
  }

  public static void init() {
    CreatureMapObjectLoader.registerCustomCreatureType(Enemy.class);
    CreatureMapObjectLoader.registerCustomCreatureType(Villager.class);
    GuiProperties.setDefaultFont(minecraft.deriveFont(32f));

    Game.config().sound().setSoundVolume(1);
    Game.config().sound().setMusicVolume(1);

    Game.world().setCamera(new PlayerCamera());

    Game.world().onLoaded(GameManager::setupMapObjects);

    switchToMap("map1");
    Game.world().environment().getSpawnpoint("spawn").spawn(Player.getInstance());
    spawn = Game.world().environment().getSpawnpoint("spawn").getLocation();
    switchToState(GameState.INGAME);
  }

  private static boolean isArena(Environment env) {
    for (final CollisionBox box : env.getCollisionBoxes()) {
      if (box.hasTag("enemyspawndata")) {
        return true;
      }
    }
    return false;
  }

  public static int MillisToTicks(int millis) {
    return Game.loop().getTickRate() * millis / 1000;
  }

  public static void removeItemEntities(Item item) {
    for (final Prop entity : Game.world().environment().getProps()) {
      if (entity instanceof ItemProp && ((ItemProp) entity).item.getName().equals(item.getName())) {
        Game.world().environment().remove(entity);
      }
    }
  }

  private static void setupMapObjects(Environment env) {
    setupTriggers(env);
    setupSpawnpoints(env);
  }

  public static void setupSpawnpoints(Environment env) {
    for (final CollisionBox infoBox : env.getCollisionBoxes()) {
      if (infoBox.hasTag("enemyspawndata")) {
        final int waveCount = infoBox.getProperties().getIntValue("waveCount");
        final int waveDelay = infoBox.getProperties().getIntValue("waveDelay");
        Spawnpoints
            .createSpawnpoints(env.getSpawnpoints().stream().filter(spawn -> spawn.hasTag("enemyspawn")).collect(Collectors.toList()), waveCount,
                waveDelay);
        return;
      }
    }

  }

  private static void setupTriggers(Environment env) {
    for (final Trigger trigger : env.getTriggers()) {
      if (trigger.hasTag("dialogue")) {
        int i = 1;
        final List<String> messages = new ArrayList<>();
        while (trigger.getProperties().hasCustomProperty("message_" + Integer.toString(i))) {
          messages.add(trigger.getProperties().getStringValue("message_" + Integer.toString(i)));
          ++i;
        }
        final int time = trigger.getProperties().getIntValue("time");
        final Dialogue dia = new Dialogue(messages.toArray(new String[messages.size()]), trigger.getX(), trigger.getY(), time);
        trigger.addActivatedListener(e -> {
          if (e.getEntity() instanceof Player) {
            ((IngameScreen) Game.screens().get("ingame")).drawDialogue(dia);
          }
        });
      }
      if (trigger.hasTag("deadly")) {
        trigger.addActivatedListener(e -> {
          final IEntity entity = e.getEntity();
          if (entity instanceof CombatEntity) {
            ((CombatEntity) entity).die();
          }
        });
      }
      if (trigger.hasTag("portal")) {
        final int cost = trigger.getProperties().getIntValue("cost");
        final String map = trigger.getProperties().getStringValue("toMap");
        final String[] coords = trigger.getProperties().getStringValue("toPos").split(",");
        trigger.addActivatedListener(e -> {
          if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getMoney() >= cost) {
            ((Player) e.getEntity()).changeMoney(-cost);
            enterPortal(map, Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()));
          }
        });
      }
      if (trigger.hasTag("portalback")) {
        final int lvl = trigger.getProperties().getIntValue("gain");
        final String map = trigger.getProperties().getStringValue("toMap");
        final String[] coords = trigger.getProperties().getStringValue("toPos").split(",");
        trigger.addActivatedListener(e -> {
          if (e.getEntity() instanceof Player && Spawnpoints.isOver()) {
            ((Player) e.getEntity()).changeLvl(lvl);
            ingame = "ingame.wav";
            Game.audio().playMusic(ingame);
            Game.loop().perform(3000, () -> enterPortal(map, Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim())));
          }
        });
      }
      if (trigger.hasTag("zoom")) {
        trigger.addActivatedListener(e -> {
          if (e.getEntity() instanceof Player) {
            final float zoom = trigger.getProperties().getFloatValue("zoomValue");
            final int duration = trigger.getProperties().hasCustomProperty("zoomDuration") ?
                trigger.getProperties().getIntValue("zoomDuration") :
                PlayerCamera.STD_DELAY;
            Game.world().camera().setZoom(zoom, duration);
          }
        });
        trigger.addDeactivatedListener(e -> {
          if (e.getEntity() instanceof Player) {
            final float zoom = PlayerCamera.STD_ZOOM;
            final int duration = trigger.getProperties().hasCustomProperty("zoomDuration") ?
                trigger.getProperties().getIntValue("zoomDuration") :
                PlayerCamera.STD_DELAY;
            Game.world().camera().setZoom(zoom, duration);
          }
        });
      }
      if (trigger.hasTag("shop")) {
        trigger.addActivatedListener(e -> {
          final IEntity entity = e.getEntity();
          if (entity instanceof Player) {
            final String shop = trigger.getProperties().getStringValue("shopName");
            entity.detachControllers();
            Shops.getShop(shop).open(() -> {
              entity.attachControllers();
            });
          }
        });
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
