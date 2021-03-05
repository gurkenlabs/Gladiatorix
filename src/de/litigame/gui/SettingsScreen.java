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
import de.gurkenlabs.litiengine.util.MathUtilities;
import de.litigame.GameManager;
import de.litigame.SaveGame;
import de.litigame.utilities.ImageUtilities;

public class SettingsScreen extends Screen implements KeyPressedListener {

	private Menu settings;
	private final ImageComponent vol_down;
	private final ImageComponent sound_bar;
	private final ImageComponent vol_up;

	public SettingsScreen() {
		super("settings");

		String[] items = { "Save Game", "Zur�ck" };

		SaveGame saveGame = new SaveGame();

		ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

		BufferedImage buttonImg = Resources.images().get("menu_item");

		Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(), buttonImg.getHeight());

		settings = new Menu((double) (Game.window().getWidth() - buttonImg.getWidth()) / 2, (double) (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2, buttonImg.getWidth(), buttonImg.getHeight() * items.length, button, items);

		int x = (int) (Game.window().getWidth() - settings.getWidth()) / 2;
		int y = (int) (settings.getY()-settings.getRowHeight());
		int margin = 11;

		vol_down = new ImageComponent(x,y,Resources.images().get("volume_down"));
		sound_bar = new ImageComponent(x+vol_down.getWidth()+margin,y,Resources.images().get("sound_bar"));
		vol_up = new ImageComponent(sound_bar.getX()+sound_bar.getWidth()+margin,y,Resources.images().get("volume_up"));

		vol_down.onClicked(componentMouseEvent -> {
			if(Game.config().sound().getMusicVolume() > 0 && Game.config().sound().getSoundVolume() > 0) {
				Game.config().sound().setMusicVolume((float) MathUtilities.round(Game.config().sound().getMusicVolume()-0.1, 1));
				Game.config().sound().setSoundVolume((float) MathUtilities.round(Game.config().sound().getSoundVolume()-0.1, 1));
			}
		});

		vol_up.onClicked(componentMouseEvent -> {
			if(Game.config().sound().getMusicVolume() < 1 && Game.config().sound().getSoundVolume() < 1) {
				Game.config().sound().setMusicVolume((float) MathUtilities.round(Game.config().sound().getMusicVolume()+0.1, 1));
				Game.config().sound().setSoundVolume((float) MathUtilities.round(Game.config().sound().getSoundVolume()+0.1, 1));
			}
		});

		settings.onChange(index -> {
			if (index == 0) {
				Game.screens().get("menu").setVisible(true);
				Game.screens().display("menu");
			}
		});
		//Game.audio().playMusic(Resources.sounds().get("sounds/menu.mp3"));
		getComponents().add(bkgr);
		getComponents().add(settings);
		getComponents().add(vol_down);
		getComponents().add(sound_bar);
		getComponents().add(vol_up);
	}

	private void renderSoundBar(Graphics2D g, int x, int y){
		int margin = 11;

		BufferedImage bar = Resources.images().get("sound_bar_fill");

		vol_down.render(g);
		sound_bar.render(g);
		vol_up.render(g);

		g.drawImage(bar, (int)(sound_bar.getX()+margin),y+margin, (int) (bar.getWidth()*Game.config().sound().getMusicVolume()),bar.getHeight(),null);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) Game.screens().display("menu");
	}

	@Override
	public void prepare() {
		super.prepare();
		for(ImageComponent cell : settings.getCellComponents()){
			cell.setFont(GameManager.getFont(72));
			cell.setHoverSound(Resources.sounds().get("sounds/mouse-over.wav"));
		}
		Game.audio().playMusic(Resources.sounds().get("sounds/menu.mp3"));
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
		renderSoundBar(g, (int) (Game.window().getWidth() - settings.getWidth()) / 2,(int) (settings.getY()-settings.getRowHeight()));
	}
}
