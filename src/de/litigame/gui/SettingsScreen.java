package de.litigame.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.utilities.ImageUtilities;

public class SettingsScreen extends Screen implements KeyPressedListener {

	public SettingsScreen() {
		super("settings");

		String[] items = { "Save Game", "Done" };

		SaveGame saveGame = new SaveGame();

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(), buttonImg.getHeight());

		Menu settings = new Menu((double) (Game.window().getWidth() - buttonImg.getWidth()) / 2, (double) (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2, buttonImg.getWidth(), buttonImg.getHeight() * items.length, button, items);

		settings.onChange(index -> {
			if (index == 0) saveGame.saveGame();
			else if (index == 1) Game.screens().display("menu");
		});

		getComponents().add(bkgr);
		getComponents().add(settings);
	}

	private void renderSoundBar(Graphics2D g, int x, int y){
		ImageComponent img = new ImageComponent(100,100, 100,100, Resources.images().get("slot"));
		img.setText("hello");
		img.prepare();
		img.render(g);
		//img.setText("-");
		//g.drawImage(img.getImage(),x,y,(int) img.getWidth(),(int) img.getHeight(),null);
		//System.out.println(img.getText());
		//g.drawString(img.getText(), (int) img.getTextX(), (int) img.getTextY());
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) Game.screens().display("menu");
	}

	@Override
	public void prepare() {
		super.prepare();
		Input.keyboard().onKeyPressed(this);

	}

	@Override
	public void suspend() {
		super.suspend();
		Input.keyboard().removeKeyPressedListener(this);
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		renderSoundBar(g, 100,100);
	}
}
