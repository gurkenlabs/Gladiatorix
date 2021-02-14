package de.litigame.hp;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerHealthBar implements IRenderable {

    private Creature creature;
    public PlayerHealthBar(Creature creature){
        this.creature = creature;
    }

    @Override
    public void render(Graphics2D g) {

        BufferedImage border = Resources.images().get("hp-border");
        BufferedImage fill = Resources.images().get("hp-fill");
        int hp = creature.getHitPoints().get();
        double fac = (double) hp/creature.getHitPoints().getMax();
        int scaling = 5;
        g.drawImage(fill,
                //Game.window().getResolution().width/2,
                (Game.window().getResolution().width - border.getWidth()*scaling) / 2,
                Game.window().getResolution().height - fill.getHeight()*scaling - 100,
                (int) (border.getWidth()*scaling*fac),
                fill.getHeight()*scaling,null);
        g.drawImage(border,
                //Game.window().getResolution().width/2,
                (Game.window().getResolution().width - border.getWidth()*scaling) / 2,
                Game.window().getResolution().height - border.getHeight()*scaling-100,
                border.getWidth()*scaling,
                border.getHeight()*scaling, null);
        //Player.getInstance().hotbar.render(g);
    }

}
