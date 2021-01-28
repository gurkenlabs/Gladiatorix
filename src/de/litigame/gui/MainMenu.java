package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.litigame.Images;

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
}
