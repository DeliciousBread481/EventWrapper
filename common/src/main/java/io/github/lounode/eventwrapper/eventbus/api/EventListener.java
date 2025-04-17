package io.github.lounode.eventwrapper.eventbus.api;

/**
 * Event listeners are wrapped with implementations of this class.
 */
public abstract sealed class EventListener
        permits ConsumerEventHandler, GeneratedEventListener, SubscribeEventListener
{
    public abstract void invoke(EventWrapper event);
}
