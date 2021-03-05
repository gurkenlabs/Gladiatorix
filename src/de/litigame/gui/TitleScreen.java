package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleScreen extends Screen {

    private final ImageComponent startGame;
    public TitleScreen() {
        super("title");

        BufferedImage button = Resources.images().get("sound_bar");

        startGame = new ImageComponent((Game.window().getWidth()-button.getWidth())/2, Game.window().getHeight()-button.getHeight()-100, button);
        startGame.setText("Spiel Starten");
        startGame.onClicked(event -> {
            suspend();
            Game.screens().display("menu");
        });
        getComponents().add(startGame);
    }

    @Override
    public void prepare() {
        super.prepare();
        startGame.setFont(GameManager.getFont(42));
        startGame.setHoverSound(Resources.sounds().get("sounds/mouse-over.wav"));
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.drawImage(Resources.images().get("title"),0,0, Game.window().getWidth(), Game.window().getHeight(), null);
        startGame.render(g);
    }
}
