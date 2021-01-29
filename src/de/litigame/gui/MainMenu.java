package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.litigame.Images;

public class MainMenu extends Screen {

    private Menu menu;
    private final int cells = 4;

    public MainMenu(final String image) {
        this(   (double) (Game.window().getWidth()-Images.get(image).getWidth())/2,
                Game.window().getHeight(),
                Images.get(image).getWidth(),
                Images.get(image).getHeight(),
                image,
                "Start Game", "Load Game", "Settings", "Exit");
    }

    public MainMenu(final double x, final double y, final double width, final double height, String image, final String... items){
        super("menu");

        Spritesheet item = new Spritesheet(Images.get(image), "screens/"+image+".jpg", 830, 199);
        ImageComponent bkgr = new ImageComponent(0,0, Images.get("menu"));
        menu = new Menu(x,y/(2* cells),width,height*cells, item, items);
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
