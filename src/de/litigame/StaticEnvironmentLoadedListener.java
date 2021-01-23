package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.EnvironmentLoadedListener;

public class StaticEnvironmentLoadedListener implements EnvironmentLoadedListener {

	private final EnvironmentLoadedListener listener;

	public StaticEnvironmentLoadedListener(EnvironmentLoadedListener onLoaded) {
		listener = onLoaded;
		Game.world().onLoaded(this);
	}

	@Override
	public void loaded(Environment environment) {
		listener.loaded(environment);
		Game.world().removeLoadedListener(this);
	}
}
