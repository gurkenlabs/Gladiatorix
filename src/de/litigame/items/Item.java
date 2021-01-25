package de.litigame.items;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.gurkenlabs.litiengine.entities.Creature;
import de.litigame.Images;

public class Item {

	public interface Consumable {
		interface ConsumeListener {
			void onConsume(Creature consumer);
		}

		default void addConsumeListener(ConsumeListener listener) {
			getConsumeListeners().add(listener);
		}

		default void consume(Creature consumer) {
			for (ConsumeListener listener : getConsumeListeners()) listener.onConsume(consumer);
		}

		List<ConsumeListener> getConsumeListeners();

		default void removeConsumeListener(ConsumeListener listener) {
			getConsumeListeners().remove(listener);
		}
	}

	public interface Stackable {
		int STACK_SIZE = 10;

		default int addAmount(int amount) {
			int newAmount = getAmount() + amount;
			if (amount > 0) {
				int excess = newAmount - STACK_SIZE;
				if (excess > 0) {
					setAmount(STACK_SIZE);
					return excess;
				}
				setAmount(newAmount);
			} else if (amount < 0) {
				int excess = -newAmount;
				if (excess > 0) {
					setAmount(0);
					return excess;
				}
				setAmount(newAmount);
			}
			return 0;
		}

		int getAmount();

		default boolean isEmpty() {
			return getAmount() <= 0;
		}

		void setAmount(int amount);
	}

	public static Stackable getStackable(Item stackable) {
		if (isStackable(stackable)) return (Stackable) stackable;
		return null;
	}

	public static boolean isStackable(Item item) {
		if (item == null) return false;
		return Arrays.asList(item.getClass().getInterfaces()).contains(Stackable.class);
	}

	protected BufferedImage image;

	protected String name;

	public Item(Map<String, String> itemInfo) {
		image = Images.get(itemInfo.get("item_image"));
		name = itemInfo.get("item_name");
	}

	public BufferedImage getImage() {
		return image;
	}

	public String getName() {
		return name;
	}
}
