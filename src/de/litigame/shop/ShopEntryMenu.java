package de.litigame.shop;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.ImageComponentList;

public class ShopEntryMenu extends ImageComponentList {

	private static List<Image> shopEntriesToImages(List<ShopEntry> entries, ShopEntry.State state) {
		List<Image> list = new ArrayList<>();
		entries.forEach(e -> list.add(e.getImage(state)));
		return list;
	}

	private final List<IntConsumer> selectionChangeConsumers = new ArrayList<>();

	public ShopEntryMenu(double x, double y, List<ShopEntry> entries, ShopEntry.State state) {
		super(x, y, entries.isEmpty() ? 0 : shopEntriesToImages(entries, state).get(0).getWidth(null),
				entries.isEmpty() ? 0 : shopEntriesToImages(entries, state).get(0).getWidth(null), entries.size(), 1,
				shopEntriesToImages(entries, state), null);
	}

	public void onChange(IntConsumer cons) {
		selectionChangeConsumers.add(cons);
	}

	@Override
	public void prepare() {
		super.prepare();
		for (ImageComponent cell : getCellComponents()) {
			cell.onClicked(e -> {
				if (!e.getEvent().isConsumed()) for (IntConsumer consumer : selectionChangeConsumers)
					consumer.accept(getComponents().indexOf(cell));
				e.getEvent().consume();
			});
		}
	}
}
