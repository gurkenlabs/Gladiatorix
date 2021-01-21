package de.litigame.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.Path;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.entities.behavior.PathFinder;
import de.gurkenlabs.litiengine.entities.behavior.PathFinder.*;
import de.gurkenlabs.litiengine.environment.GameWorld;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.hotbar.Hotbar;
import de.litigame.items.Weapon;

@AnimationInfo(spritePrefix = "NPC")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6, valign = Valign.MIDDLE)
@EntityInfo(width = 100, height = 100)
@MovementInfo(velocity = 70)

public class NPC extends Creature implements IUpdateable{

    //private NPC instance = new NPC();
    private EntityNavigator EN = new EntityNavigator(this, new AStarPathFinder(Game.world().environment().getMap()));

    public NPC() {
        super("NPC");
        Game.loop().attach(this);
    }


    public double distanceTo(Entity other) {
        return getLocation().distance(other.getLocation());
    }

    public void interact() {
        Game.world().environment().interact(this);
    }

    public boolean touches(Rectangle2D rect) {
        return this.getBoundingBox().intersects(rect);
    }

    @Override
    public void update() {
        //Path path = EN.getPathFinder().findPath(this, new Point2D.Double(200,200));
        //EN.navigate(path.getPath());
    }
}
