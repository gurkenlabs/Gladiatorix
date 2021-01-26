package de.litigame.items;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.entities.ItemProp;
import de.litigame.utilities.GeometryUtilities;
import de.litigame.utilities.ImageUtilities;

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

	protected String name;

	protected Spritesheet sprite;

	public Item(Map<String, String> itemInfo) {
		String spriteName = itemInfo.get("item_image");
		BufferedImage image = Resources.images().get(spriteName);
		sprite = new Spritesheet(image, ImageUtilities.getPath(spriteName), image.getWidth(), image.getHeight());
		name = itemInfo.get("item_name");
	}

	public void drop(Point2D location) {
		ItemProp prop = new ItemProp(this);
		prop.setLocation(location);
		prop.setLocation(GeometryUtilities.getCenterLocation(prop));
		Game.world().environment().add(prop);
	}

	public BufferedImage getImage() {
		return sprite.getImage();
	}

	public String getName() {
		return name;
	}

	public Spritesheet getSprite() {
		return sprite;
	}
}
