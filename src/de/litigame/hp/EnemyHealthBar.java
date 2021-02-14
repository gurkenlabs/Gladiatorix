package de.litigame.hp;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.attributes.RangeAttribute;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Entity;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Player;
import de.litigame.utilities.ImageUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class EnemyHealthBar implements IRenderable {

    private Creature creature;
    public EnemyHealthBar(Creature creature){
        this.creature = creature;
    }

    @Override
    public void render(Graphics2D g) {
        /*
        BufferedImage border = Resources.images().get("hp-border");
        BufferedImage fill = Resources.images().get("hp-fill");
        int hp = creature.getHitPoints().get();
        int fac = hp/creature.getHitPoints().getMax();
        g.drawImage(fill,
                (int) (creature.getX()+(Game.world().environment().getMap().getWidth()*16-Player.getInstance().getX())),
                (int) (creature.getY()+(Game.world().environment().getMap().getHeight()*8-Player.getInstance().getY())+0.4),
                (int) (border.getWidth()*0.4)*fac,
                (int) (fill.getHeight()*0.4),
                null);
        g.drawImage(border,
                (int) (creature.getX()+(Game.world().environment().getMap().getWidth()*16-Player.getInstance().getX())),
                (int) (creature.getY()+(Game.world().environment().getMap().getHeight()*8-Player.getInstance().getY())),
                (int) (border.getWidth()*0.4),
                (int) (border.getHeight()*0.4),
                null);
        */
        //DecimalFormat df = new DecimalFormat("#.#");
        int hp = creature.getHitPoints().get();
        double fac = (double) hp/creature.getHitPoints().getMax();
        String image = Math.round(fac*10)*10+"-hp";
        BufferedImage bar = Resources.images().get(image);
        g.drawImage(bar,
                (int) (creature.getX()+(Game.world().environment().getMap().getWidth()*16-Player.getInstance().getX())),
                (int) (creature.getY()+(Game.world().environment().getMap().getHeight()*8-Player.getInstance().getY())),
                (int) (bar.getWidth()),
                (int) (bar.getHeight()),
                null);
    }

}
