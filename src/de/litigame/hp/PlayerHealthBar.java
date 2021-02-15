package de.litigame.hp;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerHealthBar implements IRenderable {

    @Override
    public void render(Graphics2D g) {
        Player player = Player.getInstance();
        BufferedImage border = Resources.images().get("hp-border");
        BufferedImage fill = Resources.images().get("hp-fill");
        int hp = player.getHitPoints().get();
        double fac = (double) hp/player.getHitPoints().getMax();
        int scaling = 5;
        g.drawImage(fill,
                //Game.window().getResolution().width/2,
                (Game.window().getResolution().width - fill.getWidth()*scaling) -10,
                //Game.window().getResolution().height - fill.getHeight()*scaling - 100,
                10,
                (int) (border.getWidth()*scaling*fac),
                fill.getHeight()*scaling,null);
        g.drawImage(border,
                //Game.window().getResolution().width/2,
                (Game.window().getResolution().width - border.getWidth()*scaling) -10,
                //Game.window().getResolution().height - border.getHeight()*scaling-100,
                10,
                border.getWidth()*scaling,
                border.getHeight()*scaling, null);
        //Player.getInstance().hotbar.render(g);
    }

}
