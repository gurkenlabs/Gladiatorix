package de.litigame.hp;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.attributes.RangeAttribute;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.litigame.entities.Player;

import java.awt.*;

public class HealthBar extends GuiComponent implements IUpdateable {

    private RangeAttribute<Integer> hp;
    private double x,y;

    public HealthBar(Creature creature){
        super(creature.getX(), creature.getY());
        this.x = creature.getX();
        this.y = creature.getY();
        this.hp = creature.getHitPoints();
    }

    @Override
    public void render(Graphics2D g) {
        Player.getInstance().hotbar.render(g);
    }

    @Override
    public void update() {
        System.out.println(hp + " " + x + " " + y);
    }
}
