package de.litigame.gui;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import com.sun.tools.javac.Main;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.litigame.Images;

import javax.swing.*;

public class MainMenu extends Screen {

    private Menu menu;

    public MainMenu(final String image) {
        this(0,0, Game.window().getWidth(),Game.window().getHeight(), image);
    }

    public MainMenu(final double x, final double y, final double width, final double height, String image, final String... items){
        super("menu");
        Spritesheet bkg = new Spritesheet(Images.getRescaledCopy(Images.get(image),2), "screens/"+image+".jpg", (int) Math.round(width), (int) Math.round(height));
        this.menu = new Menu(x,y,width,height,bkg,items);
    }

    @Override
    public void render(Graphics2D g) {
        //System.out.println(this.menu.getBackground().getName());
        this.menu.render(g);
        //ImageRenderer.render(g, Images.get("menu").getScaledInstance(1920,1080,0),0,0);
        //Player.getInstance().hotbar.render(g);
    }
}
