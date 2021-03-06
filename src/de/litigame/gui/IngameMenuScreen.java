package de.litigame.gui;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.utilities.ImageUtilities;

public class IngameMenuScreen extends Screen implements KeyPressedListener {
	SaveGame saveGame = new SaveGame();

	private final Menu menu;

	public IngameMenuScreen() {
		super("ingameMenu");

		String[] items = { "Fortfahren", "Spiel speichern", "Zurueck zum Titel" };

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(), buttonImg.getHeight());

		menu = new Menu((double) (Game.window().getWidth() - buttonImg.getWidth()) / 2, (double) (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2, buttonImg.getWidth(), buttonImg.getHeight() * items.length, button, items);

		menu.onChange(index -> {
			if (index == 0) Game.screens().display("ingame");
			if (index == 1) saveGame.saveGame();
			if (index == 2) Game.screens().display("menu");
		});

		getComponents().add(bkgr);
		getComponents().add(menu);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) Game.screens().display("ingame");
	}

	@Override
	public void prepare() {
		super.prepare();
		for (ImageComponent cell : menu.getCellComponents()) {
			cell.setFont(GameManager.getFont(72));
			cell.setHoverSound(Resources.sounds().get("mouse-over.wav"));
		}
		Input.keyboard().onKeyPressed(this);
	}

	@Override
	public void suspend() {
		super.suspend();
		Input.keyboard().removeKeyPressedListener(this);
	}
}
