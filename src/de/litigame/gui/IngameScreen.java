package de.litigame.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.GameManager;
import de.litigame.entities.Player;
import de.litigame.graphics.Dialogue;

public class IngameScreen extends GameScreen implements KeyPressedListener {

	private Dialogue dialogue;
	public final List<IRenderable> overlayMenus = new ArrayList<>();

	public IngameScreen() {
		super("ingame");
	}

	public void addOverlayMenu(IRenderable menu) {
		overlayMenus.add(menu);
	}

	public void drawDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
		dialogue.prepare();
	}

	public void removeOverlayMenu(IRenderable menu) {
		overlayMenus.remove(menu);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Game.screens().display("ingameMenu");
		}
	}

	@Override
	public void prepare() {
		super.prepare();
		Game.audio().stopMusic();
		Game.audio().playMusic(Resources.sounds().get(GameManager.ingame));

		//Player.getInstance().changeMoney(-100);
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
		if (dialogue != null) {
			if (!dialogue.shouldBeDrawn()) {
				dialogue.suspend();
				dialogue = null;
			} else {
				dialogue.render(g);
			}
		}
		g.drawImage(Resources.images().get("coin"), 10, 10,100,100, null);
		g.setFont(GameManager.getFont(72));
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(Player.getInstance().getMoney()),120,80);
		Player.getInstance().healthBar.render(g);
		Player.getInstance().hotbar.render(g);

		for (final IRenderable menu : overlayMenus) {
			menu.render(g);
		}
	}
}
