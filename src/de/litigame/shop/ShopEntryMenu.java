package de.litigame.shop;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import de.gurkenlabs.litiengine.gui.ImageComponent;

public class ShopEntryMenu extends ResizableImageComponentList {

	private static List<Image> shopEntriesToImages(List<ShopEntry> entries, ShopEntry.State state) {
		List<Image> list = new ArrayList<>();
		entries.forEach(e -> list.add(e.getImage(state)));
		return list;
	}

	private final List<IntConsumer> selectionChangeConsumers = new ArrayList<>();
	private final ShopEntry.State state;

	public ShopEntryMenu(double x, double y, double width, double height, ShopEntry.State state) {
		super(x, y, width, height, null);
		this.state = state;
	}

	public void onChange(IntConsumer cons) {
		selectionChangeConsumers.add(cons);
	}

	public void updateEntries(List<ShopEntry> entries) {
		suspend();
		System.out.println("components: " + getComponents() + "  cells: " + getCellComponents());
		getImages().clear();
		getImages().addAll(shopEntriesToImages(entries, state));
		System.out.println("images: " + getImages());
		for (ImageComponent g : getCellComponents()) System.out.println(g + "  " + getCellComponents().indexOf(g));
		prepare();

		for (int i = 0; i < getCellComponents().size(); i++) {
			ImageComponent menuButton = getCellComponents().get(i);
			final int s = i;
			menuButton.onMousePressed(c -> {
				for (IntConsumer consumer : selectionChangeConsumers) consumer.accept(s);

			});
		}
		for (ImageComponent i : getCellComponents()) System.out.println(i + "  " + getCellComponents().indexOf(i));
	}
}
