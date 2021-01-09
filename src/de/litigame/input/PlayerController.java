package de.litigame.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.util.ListUtilities;
import de.litigame.entities.Player;

public class PlayerController extends KeyboardEntityController<Player>
		implements KeyPressedListener, MouseWheelListener {

	private List<Integer> attackKeys;
	private final HotbarController hotbarController;
	private List<Integer> interactKeys;
	private final Player player;

	public PlayerController() {
		this(Player.getInstance(), KeyEvent.VK_SPACE, KeyEvent.VK_F);
	}

	public PlayerController(Player player, int attack, int interact) {
		super(player);
		this.player = player;
		hotbarController = new HotbarController(player.hotbar);
		attackKeys = new ArrayList<>();
		interactKeys = new ArrayList<>();

		attackKeys.add(attack);
		interactKeys.add(interact);
		player.addController(this);
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
		// prevents uncontrollable behavior from built-in method
	}

	@Override
	public void keyPressed(KeyEvent event) {
		super.handlePressedKey(event);

		if (attackKeys.contains(event.getKeyCode())) player.attack();
		if (interactKeys.contains(event.getKeyCode())) player.interact();

		hotbarController.keyPressed(event);
		event.consume();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		hotbarController.mouseWheelMoved(event);
	}

	public void setAttackKeys(int... attack) {
		attackKeys = ListUtilities.getIntList(attack);
	}

	public void setInteractKeys(int... interact) {
		interactKeys = ListUtilities.getIntList(interact);
	}
}
