package io.github.lounode.eventwrapper.eventbus.api;

/**
 * Event listeners are wrapped with implementations of this interface
 */
public interface IEventListener {
	void invoke(EventWrapper event);

	default String listenerName() {
		return getClass().getName();
	}
}
