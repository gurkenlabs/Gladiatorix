package de.litigame.hotbar;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.litigame.items.Item;
import de.litigame.items.Item.Stackable;
import de.litigame.utilities.ImageUtilities;

public class Hotbar implements IRenderable {

	private final Item[] items;
	private final IEntity owner;
	private int selectedSlot = 0;

	public Hotbar(IEntity owner) {
		this(9, owner);
	}

	public Hotbar(int size, IEntity owner) {
		items = new Item[size];
		this.owner = owner;
	}

	public boolean addItem(Item item) {
		for (int i = 0; i < items.length; ++i) {
			if (items[i] == null) {
				items[i] = item;
				removeEmptyItems();
				return true;
			} else if (items[i].getName().equals(item.getName()) && Item.isStackable(item)) {
				int excess = Item.getStackable(items[i]).addAmount(Item.getStackable(item).getAmount());
				Item.getStackable(item).setAmount(excess);
				removeEmptyItems();
				if (excess == 0) return true;
			}
		}
		return false;
	}

	public void replace(Item item, int i) {
		items[i] = item;
	}

	public int getSelectedSlot() {return selectedSlot;}


	public boolean pickUpItem(Item item){
		int i = selectedSlot;
		if (items[i] == null) {
			items[i] = item;
			removeEmptyItems();
			return true;
		} else if (items[i].getName().equals(item.getName()) && Item.isStackable(item)) {
			int excess = Item.getStackable(items[i]).addAmount(Item.getStackable(item).getAmount());
			Item.getStackable(item).setAmount(excess);
			removeEmptyItems();
			if (excess == 0) return true;
		}
		return false;
	}

	public void addToPosition(int shift) {
		selectedSlot += shift;
		checkBounds();
	}

	private void checkBounds() {
		if (selectedSlot < 0) selectedSlot = 0;
		else if (selectedSlot >= size()) selectedSlot = size() - 1;
	}

	public void dropSelectedItem() {
		if (getSelectedItem() == null) return;
		getSelectedItem().drop(owner.getCenter());
		items[selectedSlot] = null;
	}

	public Item getSelectedItem() {
		return items[selectedSlot];
	}

	public void giveItem(Item item) {
		if (!addItem(item)) item.drop(owner.getCenter());
	}

	private void removeEmptyItems() {
		for (int i = 0; i < items.length; ++i) {
			if (Item.isStackable(items[i]) && ((Stackable) items[i]).isEmpty()) items[i] = null;
		}
	}

	public void removeItems(Item item) {
		for (int i = 0; i < items.length; ++i)
			if (items[i] != null && items[i].getName().equals(item.getName())) items[i] = null;
	}

	@Override
	public void render(Graphics2D graphics) {
		BufferedImage slot = Resources.images().get("slot");
		BufferedImage image = new BufferedImage(slot.getWidth() * size(), slot.getHeight(), slot.getType());
		Graphics2D g = image.createGraphics();

		for (int i = 0; i < size(); ++i) {
			int x = i * slot.getWidth(), y = 0;
			g.drawImage(slot, x, y, null);
			if (i == selectedSlot) g.drawImage(Resources.images().get("selected_slot"), x, y, null);
			if (items[i] != null) g.drawImage(items[i].getImage(), x, y, null);
		}

		g.dispose();

		image = ImageUtilities.getRescaledCopy(image, 3);

		graphics.drawImage(image, (Game.window().getResolution().width - image.getWidth()) / 2,
				Game.window().getResolution().height - image.getHeight(), null);
	}

	public void setToSlot(int slot) {
		selectedSlot = slot;
		checkBounds();
	}

	public int size() {
		return items.length;
	}

	public String parse() {

		Object[] itemNames = Arrays.stream(items).map(a -> {
			if(a!=null) return a.getName();
			else return "null";
		}).toArray();

		String[] itms = Arrays.copyOf(itemNames,itemNames.length,String[].class);
		return Arrays.toString(itms).substring(1, Arrays.toString(itms).length()-1);
	}
}
