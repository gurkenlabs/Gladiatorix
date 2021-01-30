package de.litigame.entities;

import de.gurkenlabs.litiengine.entities.Creature;

public class Villager extends Creature implements IInteractEntity {

	public Villager() {
		super("villager");
		addToWorld();
	}

	@Override
	public void interact(Player player) {
		// TODO Auto-generated method stub
	}
}
