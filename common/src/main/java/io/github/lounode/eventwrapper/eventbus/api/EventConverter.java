package io.github.lounode.eventwrapper.eventbus.api;

/**
 * Convent an event to a wrapper
 * 
 * @param <E> Custom Event
 * @param <W> Wrapper
 */
public interface EventConverter<E, W extends EventWrapper> {
	/**
	 *
	 * @param event original event
	 * @return wrapper
	 */
	W toWrapper(E event);

	/**
	 *
	 * @param wrapper
	 * @return custom event
	 */
	E toEvent(W wrapper);
}
