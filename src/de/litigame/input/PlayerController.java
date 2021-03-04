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
	private boolean canInteract = true;
	private List<Integer> dropKeys = new ArrayList<>();
	private final HotbarController hotbarController;
	private List<Integer> interactKeys = new ArrayList<>();
	private final Player player;

	public PlayerController() {
		this(Player.getInstance());
	}

	public PlayerController(Player player) {
		this(player, KeyEvent.VK_SPACE, KeyEvent.VK_Q, KeyEvent.VK_F);
	}

	public PlayerController(Player player, int attack, int drop, int interact) {
		super(player);
		this.player = player;
		hotbarController = new HotbarController(player.hotbar);

		addAttackKey(attack);
		addDropKey(drop);
		addInteractKey(interact);

		Input.keyboard().onKeyReleased(this::handleReleasedKey);
		Input.mouse().onWheelMoved(hotbarController::handleMovedWheel);
	}

	public void addAttackKey(int keyCode) {
		if (!attackKeys.contains(keyCode)) attackKeys.add(keyCode);
	}

	public void addDropKey(int keyCode) {
		if (!dropKeys.contains(keyCode)) dropKeys.add(keyCode);
	}

	public void addInteractKey(int keyCode) {
		if (!interactKeys.contains(keyCode)) interactKeys.add(keyCode);
	}

	public List<Integer> getAttackKeys() {
		return attackKeys;
	}

	public List<Integer> getDropKeys() {
		return dropKeys;
	}

	public List<Integer> getInteractKeys() {
		return interactKeys;
	}

	@Override
	public void handlePressedKey(KeyEvent event) {
		super.handlePressedKey(event);

		if (attackKeys.contains(event.getKeyCode())) player.attack();
		if (dropKeys.contains(event.getKeyCode())) player.dropItem();
		if (interactKeys.contains(event.getKeyCode()) && canInteract) {
			player.interact();
			canInteract = false;
		}

		hotbarController.handlePressedKey(event);
	}

	public void handleReleasedKey(KeyEvent event) {
		if (interactKeys.contains(event.getKeyCode())) canInteract = true;
	}

	public void setAttackKeys(int... attack) {
		attackKeys = ListUtilities.getIntList(attack);
	}

	public void setDropKeys(int... drop) {
		dropKeys = ListUtilities.getIntList(drop);
	}

	public void setInteractKeys(int... interact) {
		interactKeys = ListUtilities.getIntList(interact);
	}
}
