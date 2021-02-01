package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.Creature;

public class Villager extends Creature implements InteractListener {

	public Villager() {
		super("villager");
		Player.getInstance().addInteractListener(this);
	}

	@Override
	public void interact(Player player) {
		// TODO Auto-generated method stub
	}
}
