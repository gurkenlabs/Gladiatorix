package de.litigame.items;

public interface Stackable {
	int STACK_SIZE = 10;

	boolean addAmount(int amount);

	int getAmount();

	boolean isEmpty();
}
