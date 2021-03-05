package de.litigame.animations;

import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.AnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;

public class EntityAnimationController<T extends IEntity> extends AnimationController
		implements IEntityAnimationController<T> {
	protected static class AnimationRule<T extends IEntity> {
		private final Function<? super T, String> animationName;
		private final Predicate<? super T> condition;
		private int priority;

		public AnimationRule(Predicate<? super T> condition, Function<? super T, String> animationName, int priority) {
			this.condition = condition;
			this.animationName = animationName;
			this.priority = priority;
		}

		Function<? super T, String> getAnimationName() {
			return this.animationName;
		}

		Predicate<? super T> getCondition() {
			return this.condition;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}
	}

	public static String[] getDefaultSpritePrefixes(Class<?> cls) {
		AnimationInfo animationInfo = cls.getAnnotation(AnimationInfo.class);
		if (animationInfo != null && animationInfo.spritePrefix().length > 0) {
			return animationInfo.spritePrefix();
		} else {
			return new String[] { cls.getSimpleName().toLowerCase() };
		}
	}

	private final List<AnimationRule<T>> animationRules = new CopyOnWriteArrayList<>();
	private boolean autoScaling;

	private final T entity;

	private String spritePrefix;

	/**
	 * Initializes a new instance of the {@code EntityAnimationController} class.
	 *
	 * @param entity The entity related to this animation controller.
	 *
	 * @see #getEntity()
	 */
	public EntityAnimationController(final T entity) {
		super();
		this.entity = entity;

		if (entity != null) {
			this.spritePrefix = Game.random().choose(getDefaultSpritePrefixes(entity.getClass()));
		}
	}

	/**
	 * Initializes a new instance of the {@code EntityAnimationController} class.
	 *
	 * @param entity           The entity related to this animation controller.
	 *
	 * @param defaultAnimation The default animation for this controller.
	 *
	 * @param animations       Additional animations that are managed by this
	 *                         controller instance.
	 *
	 * @see #getEntity()
	 * @see #getDefault()
	 * @see #getAll()
	 */
	public EntityAnimationController(final T entity, final Animation defaultAnimation, final Animation... animations) {
		super(defaultAnimation, animations);
		this.entity = entity;

		this.spritePrefix = Game.random().choose(getDefaultSpritePrefixes(entity.getClass()));
	}

	/**
	 * Initializes a new instance of the {@code EntityAnimationController} class.
	 *
	 * @param entity The entity related to this animation controller.
	 *
	 * @param sprite The sprite sheet used by the default animation of this
	 *               controller.
	 */
	public EntityAnimationController(final T entity, final Spritesheet sprite) {
		this(entity, sprite, true);
	}

	/**
	 * Initializes a new instance of the {@code EntityAnimationController} class.
	 *
	 * @param entity The entity related to this animation controller.
	 *
	 * @param sprite The sprite sheet used by the default animation of this
	 *               controller.
	 * @param loop   A flag indicating whether the default animation should be
	 *               looped or only played once.
	 */
	public EntityAnimationController(final T entity, final Spritesheet sprite, boolean loop) {
		this(entity, new Animation(sprite, loop, Resources.spritesheets().getCustomKeyFrameDurations(sprite)));
	}

	@Override
	public void addRule(Predicate<? super T> rule, Function<? super T, String> animationName) {
		this.addRule(rule, animationName, 0);
	}

	@Override
	public synchronized void addRule(Predicate<? super T> rule, Function<? super T, String> animationName,
			int priority) {
		// binary search the list for the appropriate index
		int min = 0;
		int max = this.animationRules.size();
		while (min < max - 1) {
			int midpoint = (min + max) / 2;
			if (priority > this.animationRules.get(midpoint).getPriority()) {
				min = midpoint + 1;
			} else {
				max = midpoint;
			}
		}

		this.animationRules.add(min, new AnimationRule<>(rule, animationName, priority));
	}

	@Override
	public T getEntity() {
		return this.entity;
	}

	protected String getSpritePrefix() {
		return this.spritePrefix;
	}

	@Override
	public boolean isAutoScaling() {
		return this.autoScaling;
	}

	@Override
	public void scaleSprite(float scale) {
		this.scaleSprite(scale, scale);
	}

	@Override
	public void scaleSprite(float scaleX, float scaleY) {
		AffineTransform trans = new AffineTransform();
		trans.scale(scaleX, scaleY);

		setAffineTransform(trans);
	}

	@Override
	public void setAutoScaling(boolean scaling) {
		this.autoScaling = scaling;
	}

	protected void setSpritePrefix(String prefix) {
		this.spritePrefix = prefix;
	}

	@Override
	public void update() {

		for (final Animation animation : getAll()) {
			animation.update();
		}

		if (getCurrent() != null && getCurrent().isPaused()) {
			return;
		}

		if (Game.world().environment() == null || Game.world().environment().getMap() == null) {
			return;
		}

		if (getCurrent() != null && !getCurrent().isLooping() && getCurrent().isPlaying()) {
			return;
		}

		if (this.getEntity() == null) {
			return;
		}

		for (AnimationRule<T> animationRule : this.animationRules) {
			if (animationRule.getCondition().test(this.getEntity())) {
				final String animationName = animationRule.getAnimationName().apply(this.getEntity());
				if (getCurrent() == null
						|| (!getCurrent().isPlaying() && getCurrent().getName().equalsIgnoreCase(animationName))
						|| (!animationName.isEmpty() && !getCurrent().getName().equalsIgnoreCase(animationName))) {
					play(animationName);
				}

				break;
			}
		}
	}
}
