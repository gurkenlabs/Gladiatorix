package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.configuration.DisplayMode;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.litigame.Images;

public class SettigsScreen extends Screen {
    private Menu menu;
    private final int cells = 2;
    public SettigsScreen(final String image) {
        this(   (double) (Game.window().getWidth()-Images.get(image).getWidth())/2,
                Game.window().getHeight(),
                Images.get(image).getWidth(),
                Images.get(image).getHeight(),
                image,
                "Fullscreen: On", "Done");
    }

    public SettigsScreen(final double x, final double y, final double width, final double height, String image, final String... items){
        super("settings");
        Spritesheet item = new Spritesheet(Images.get(image), "screens/"+image+".jpg", 830, 199);
        ImageComponent bkgr = new ImageComponent(0,0, Images.get("menu"));
        menu = new Menu(x,y/(2* cells),width,height*cells, item, items);
        menu.prepare();
        menu.onChange(index -> {
            if (index == 0) switchDisplayMode();
            if (index == 1) Game.screens().display("menu");
        });
        getComponents().add(bkgr);
        getComponents().add(menu);
    }

    private void switchDisplayMode() {
        if (Game.config().graphics().getDisplayMode() == DisplayMode.BORDERLESS) {
            System.out.println(Game.config().graphics().getDisplayMode());
            Game.config().graphics().setDisplayMode(DisplayMode.WINDOWED);
        }else{
            Game.config().graphics().setDisplayMode(DisplayMode.BORDERLESS);
        }
    }
}
