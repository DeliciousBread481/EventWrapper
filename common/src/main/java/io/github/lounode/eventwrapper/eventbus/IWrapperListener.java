package io.github.lounode.eventwrapper.eventbus;

import io.github.lounode.eventwrapper.eventbus.api.EventListener;

/**
 * Listener that wraps a listener to add a check.
 */
public interface IWrapperListener {
    EventListener getWithoutCheck();
}
