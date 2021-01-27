package de.litigame.gui;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import com.sun.tools.javac.Main;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.ImageComponent;
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
        Spritesheet item = new Spritesheet(Images.get(image), "screens/"+image+".jpg", 830, 199);
        menu = new Menu(x,y,width,height, item, "Start Game", "Load Game", "Settings", "Exit");
        ImageComponent bkgr = new ImageComponent(0,0, Images.get("menu"));
        getComponents().add(bkgr);
        getComponents().add(menu);
    }

    //@Override

    //public void render(Graphics2D g) {
        //Spritesheet img = new Spritesheet(Images.get("menu_item"), "screens/menu_item.jpg", 1, 1);
        /*System.out.println(this.menu.getBackground())
          ImageRenderer.render(g, this.menu.getBackground().getImage().getScaledInstance(1920,1080,0),0,0);
          menu.prepare();
          ImageRenderer.render(g, Images.get("menu").getScaledInstance(1920,1080,0),0,0);
          Player.getInstance().hotbar.render(g);
        menu.render(g);*/
    //}
}
