package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.litigame.Images;

import java.awt.image.BufferedImage;

public class MainMenu extends Screen {

    private Menu menu;
    private final int cells = 4;
    /*public MainMenu(final String image, String... items) {
        this(   (double) (Game.window().getWidth()-Images.get(image).getWidth())/2,
                (double)Game.window().getHeight()/(2* items.length),
                Images.get(image).getWidth(),
                Images.get(image).getHeight()*items.length,
                image,
                items);
    }

    public MainMenu(final double x, final double y, final double width, final double height, String image, final String... items){
        super("menu");
        Spritesheet item = new Spritesheet(Images.get(image), "screens/"+image+".jpg", 830, 199);
        menu = new Menu(x,y,width,height, item, items);
        ImageComponent bkgr = new ImageComponent(0,0, Images.get("menu"));
        menu.prepare();
        menu.onChange(index -> {
            if (index == 0) Game.screens().display("ingame");
            else if (index == 2) Game.screens().display("settings");
            else if (index == 3) System.exit(0);
        });
        getComponents().add(bkgr);
        getComponents().add(menu);
    }*/
    public MainMenu(){
        super("menu");
        BufferedImage img = Images.get("menu_item");
        Spritesheet item = new Spritesheet(img, "screens/menu_item.jpg", img.getWidth(), img.getHeight());
        menu = new Menu(
                (double) (Game.window().getWidth()-img.getWidth())/2,
                (double) Game.window().getHeight()/(2* cells),
                img.getWidth(),
                img.getHeight()*cells,
                item,
                "Start Game", "Load Game", "Settings", "Exit Game");
        ImageComponent bkgr = new ImageComponent(0,0, Images.get("menu"));
        menu.prepare();
        menu.onChange(index -> {
            if (index == 0) Game.screens().display("ingame");
            else if (index == 2) Game.screens().display("settings");
            else if (index == 3) System.exit(0);
        });
        getComponents().add(bkgr);
        getComponents().add(menu);
    }
}
