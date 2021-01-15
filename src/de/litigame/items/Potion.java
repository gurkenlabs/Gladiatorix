package de.litigame.items;

import java.util.Map;

public class Potion extends Item implements Stackable {

	private int amount = 1;

	public Potion(Map<String, String> itemInfo) {
		super(itemInfo);
	}

	@Override
	public boolean addAmount(int amount) {
		int test = this.amount + amount;
		if (test > STACK_SIZE || test < 0) return false;
		this.amount = test;
		return true;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public boolean isEmpty() {
		return amount <= 0;
	}
}
