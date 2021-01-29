package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.litigame.Images;

public class SettigsScreen extends Screen {
    private Menu menu;
    public SettigsScreen(final String image, String... items) {
        this((double) Game.window().getWidth()/2-415,(double)Game.window().getHeight()/(2* items.length),830,199* items.length, image, items);
    }

    public SettigsScreen(final double x, final double y, final double width, final double height, String image, final String... items){
        super("settings");
        Spritesheet item = new Spritesheet(Images.get(image), "screens/"+image+".jpg", 830, 199);
        menu = new Menu(x,y,width,height, item, items);
        ImageComponent bkgr = new ImageComponent(0,0, Images.get("menu"));
        menu.prepare();
        menu.onChange(index -> {
            if (index == 0) Game.screens().display("menu");
        });
        getComponents().add(bkgr);
        getComponents().add(menu);
    }
}
