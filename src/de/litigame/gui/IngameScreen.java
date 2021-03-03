package de.litigame.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.litigame.entities.Player;
import de.litigame.graphics.Dialogue;

public class IngameScreen extends GameScreen {

	private final List<Dialogue> dialogues = new ArrayList<>();
	private final List<IRenderable> overlayMenus = new ArrayList<>();

	public IngameScreen() {
		super("ingame");
	}

	public void addOverlayMenu(IRenderable menu) {
		overlayMenus.add(menu);
	}

	public void drawDialogue(Dialogue dialogue) {
		dialogues.add(dialogue);
		dialogue.prepare();
	}

	public void removeOverlayMenu(IRenderable menu) {
		overlayMenus.remove(menu);
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);

		for (Dialogue dialogue : dialogues) {
			if (!dialogue.shouldBeDrawn()) {
				dialogues.remove(dialogue);
				dialogue.suspend();
			} else {

			}
		}

		Player.getInstance().healthBar.render(g);
		Player.getInstance().hotbar.render(g);

		for (IRenderable menu : overlayMenus) menu.render(g);
	}
}
