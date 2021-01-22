package de.litigame.input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.util.ListUtilities;
import de.litigame.entities.Player;

public class PlayerController extends KeyboardEntityController<Player> {

	private List<Integer> attackKeys = new ArrayList<>();
	private final HotbarController hotbarController;
	private List<Integer> interactKeys = new ArrayList<>();
	private final Player player;

	public PlayerController() {
		this(Player.getInstance());
	}

	public PlayerController(Player player) {
		this(player, KeyEvent.VK_SPACE, KeyEvent.VK_F);
	}

	public PlayerController(Player player, int attack, int interact) {
		super(player);
		this.player = player;
		hotbarController = new HotbarController(player.hotbar);

		addAttackKey(attack);
		addInteractKey(interact);

		Input.mouse().onWheelMoved(hotbarController::handleMovedWheel);
	}

	public void addAttackKey(int keyCode) {
		if (!attackKeys.contains(keyCode)) attackKeys.add(keyCode);
	}

	public void addInteractKey(int keyCode) {
		if (!interactKeys.contains(keyCode)) interactKeys.add(keyCode);
	}

	public List<Integer> getAttackKeys() {
		return attackKeys;
	}

	public List<Integer> getInteractKeys() {
		return interactKeys;
	}

	@Override
	public void handlePressedKey(KeyEvent event) {
		super.handlePressedKey(event);

		if (attackKeys.contains(event.getKeyCode())) player.attack();
		if (interactKeys.contains(event.getKeyCode())) player.interact();

		hotbarController.keyPressed(event);
	}

	public void setAttackKeys(int... attack) {
		attackKeys = ListUtilities.getIntList(attack);
	}

	public void setInteractKeys(int... interact) {
		interactKeys = ListUtilities.getIntList(interact);
	}
}
