package de.litigame.gui;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.JAXBException;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.XmlUtilities;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.entities.Player;

public class GameOverScreen extends Screen {

	private final ImageComponent startGame;

	public GameOverScreen() {
		super("game_over");
		BufferedImage button = Resources.images().get("sound_bar.png");

		startGame = new ImageComponent((Game.window().getWidth() - button.getWidth()) / 2, Game.window().getHeight() - button.getHeight() - 100, button);
		startGame.setText("Nochmal Spielen");
		startGame.onClicked(event -> {
			suspend();
			GameManager.switchToMap("map1");
			try {
				final String path = "respawnsave.xml";
				final SaveGame saveGame = XmlUtilities.read(SaveGame.class, Resources.getLocation(path));
				Player.getInstance().init(saveGame.getHotbar(), saveGame.getHealth(), saveGame.getMoney(), saveGame.getLocation(), saveGame.getHealth(), saveGame.getSlot());
			} catch (final JAXBException e) {
			}
			Player.getInstance().resurrect();
			Game.screens().display("menu");
		});
		getComponents().add(startGame);
	}

	@Override
	public void prepare() {
		super.prepare();
		Game.audio().stopMusic();
		GameManager.init();
		GameManager.spawn = new Point2D() {
			@Override
			public double getX() {
				return 2000;
			}

			@Override
			public double getY() {
				return 2000;
			}

			@Override
			public void setLocation(double x, double y) {

			}
		};
		startGame.setHoverSound(Resources.sounds().get("mouse-over.wav"));
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.drawImage(Resources.images().get("game_over.png"), 0, 0, Game.window().getWidth(), Game.window().getHeight(), null);
		startGame.render(g);
	}
}
