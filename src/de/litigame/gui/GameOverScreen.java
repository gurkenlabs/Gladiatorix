package de.litigame.gui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.entities.Player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class GameOverScreen extends Screen {

    private final ImageComponent startGame;
    public GameOverScreen() {
        super("game_over");

        SaveGame saveGame = new SaveGame();
        BufferedImage button = Resources.images().get("sound_bar");

        startGame = new ImageComponent((Game.window().getWidth()-button.getWidth())/2, Game.window().getHeight()-button.getHeight()-100, button);
        startGame.setText("Nochmal Spielen");
        startGame.onClicked(event -> {
            suspend();
            final String[] initialItems = { "Trainingsschwert", "null", "null", "null", "null" };
            Game.screens().display("menu");
        });
        getComponents().add(startGame);
    }

    @Override
    public void prepare() {
        super.prepare();
        Game.audio().stopMusic();
        startGame.setFont(GameManager.getFont(42));
        startGame.setHoverSound(Resources.sounds().get("sounds/mouse-over.wav"));
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.drawImage(Resources.images().get("game_over"),0,0, Game.window().getWidth(), Game.window().getHeight(), null);
        startGame.render(g);
    }
}
