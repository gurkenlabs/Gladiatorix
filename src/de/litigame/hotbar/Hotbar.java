package de.litigame.hotbar;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import de.litigame.items.Item;
import de.litigame.items.Item.Stackable;

public class Hotbar implements IRenderable {

  private final Item[] items;
  private final IEntity owner;
  private int selectedSlot = 0;

  public Hotbar(IEntity owner) {
    this(5, owner);
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
        final int excess = Objects.requireNonNull(Item.getStackable(items[i])).addAmount(Objects.requireNonNull(Item.getStackable(item)).getAmount());
        Objects.requireNonNull(Item.getStackable(item)).setAmount(excess);
        removeEmptyItems();
        if (excess == 0) {
          return true;
        }
      }
    }
    return false;
  }

  public void addToPosition(int shift) {
    selectedSlot += shift;
    checkBounds();
  }

  private void checkBounds() {
    if (selectedSlot < 0) {
      selectedSlot = 0;
    } else if (selectedSlot >= size()) {
      selectedSlot = size() - 1;
    }
  }

  public void dropSelectedItem() {
    if (getSelectedItem() == null) {
      return;
    }
    getSelectedItem().drop(owner.getCenter());
    items[selectedSlot] = null;
  }

  public Item getSelectedItem() {
    return items[selectedSlot];
  }

  public int getSelectedSlot() {
    return selectedSlot;
  }

  public void giveItem(Item item) {
    if (!addItem(item)) {
      item.drop(owner.getCenter());
    }
  }

  public String parse() {

    final Object[] itemNames = Arrays.stream(items).map(a -> {
      if (a != null) {
        return a.getName();
      } else {
        return "null";
      }
    }).toArray();

    final String[] itms = Arrays.copyOf(itemNames, itemNames.length, String[].class);
    return Arrays.toString(itms).substring(1, Arrays.toString(itms).length() - 1);
  }

  public void removeEmptyItems() {
    for (int i = 0; i < items.length; ++i) {
      if (Item.isStackable(items[i]) && ((Stackable) items[i]).isEmpty()) {
        items[i] = null;
      }
    }
  }

  public void removeItems(Item item) {
    for (int i = 0; i < items.length; ++i) {
      if (items[i] != null && items[i].getName().equals(item.getName())) {
        items[i] = null;
      }
    }
  }

  @Override
  public void render(Graphics2D graphics) {
    final BufferedImage slot = Resources.images().get("slot.png");
    BufferedImage image = new BufferedImage(slot.getWidth() * size(), slot.getHeight(), slot.getType());
    final Graphics2D g = image.createGraphics();

    for (int i = 0; i < size(); ++i) {
      final int x = i * slot.getWidth();
      final int y = 0;
      g.drawImage(slot, x, y, null);
      if (i == selectedSlot) {
        g.drawImage(Resources.images().get("sel_slot.png"), x + (slot.getWidth() - Resources.images().get("sel_slot.png").getWidth()) / 2,
            y + (slot.getHeight() - Resources.images().get("sel_slot.png").getHeight()) / 2, null);
      }
      if (items[i] != null) {
        g.drawImage(items[i].getImage(), x + (slot.getWidth() - items[i].getImage().getWidth()) / 2,
            y + (slot.getHeight() - items[i].getImage().getHeight()) / 2, null);
      }
    }

    g.dispose();

    image = Imaging.scale(image, 3);

    graphics.drawImage(image, (Game.window().getResolution().width - image.getWidth()) / 2, Game.window().getResolution().height - image.getHeight(),
        null);
  }

  public void replace(Item item, int i) {
    items[i] = item;
  }

  public void setToSlot(int slot) {
    selectedSlot = slot;
    checkBounds();
  }

  public int size() {
    return items.length;
  }
}
