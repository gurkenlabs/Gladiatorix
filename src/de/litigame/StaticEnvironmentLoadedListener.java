package de.litigame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.EnvironmentLoadedListener;

public class StaticEnvironmentLoadedListener implements EnvironmentLoadedListener {

	public static void attach(EnvironmentLoadedListener onLoaded) {
		Game.world().onLoaded(new StaticEnvironmentLoadedListener(onLoaded));
	}

	private final EnvironmentLoadedListener listener;

	private StaticEnvironmentLoadedListener(EnvironmentLoadedListener onLoaded) {
		listener = onLoaded;
	}

	@Override
	public void loaded(Environment environment) {
		listener.loaded(environment);
		Game.world().removeLoadedListener(this);
	}
}
