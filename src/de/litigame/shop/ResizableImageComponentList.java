package de.litigame.shop;

import java.awt.Image;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.gui.ImageComponent;

public class ResizableImageComponentList extends GuiComponent {

	private final List<ImageComponent> cells;
	private final double columnWidth;
	private List<Image> images;
	private final double rowHeight;

	public ResizableImageComponentList(final double x, final double y, final double width, final double height,
			final List<Image> images) {
		super(x, y, width, height);
		if (images != null) {
			this.images = images;
		} else {
			this.images = new CopyOnWriteArrayList<>();
			this.images.add(null);
		}

		cells = new CopyOnWriteArrayList<>();

		rowHeight = height;
		columnWidth = width;
	}

	public List<ImageComponent> getCellComponents() {
		return cells;
	}

	public List<Image> getImages() {
		return images;
	}

	@Override
	public void prepare() {
		for (int y = 0; y < getImages().size(); y++) {
			ImageComponent cell = new ImageComponent(getX(), getY() + y * rowHeight, columnWidth, rowHeight, null, "",
					getImages().get(y));

			cells.add(cell);
		}
		getComponents().addAll(cells);
		super.prepare();
	}

	@Override
	public void suspend() {
		for (ImageComponent cell : getCellComponents()) cell.suspend();
		for (GuiComponent cell : getComponents()) cell.suspend();
		super.suspend();
		cells.clear();
		getComponents().clear();
	}
}