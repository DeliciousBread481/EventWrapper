package io.github.lounode.eventwrapper.eventbus.api;

import io.github.lounode.eventwrapper.eventbus.EventListenerFactory;
import io.github.lounode.eventwrapper.eventbus.IWrapperListener;

import java.lang.reflect.*;
import static org.objectweb.asm.Type.getMethodDescriptor;

/**
 * Wrapper around an event handler generated for a {@link SubscribeEventWrapper} method.
 */
public final class SubscribeEventListener extends EventListener implements IWrapperListener {
    private final EventListener handler;
    private final SubscribeEventWrapper subInfo;
    private String readable;

    public SubscribeEventListener(Object target, Method method) {
        handler = EventListenerFactory.create(method, target);

        subInfo = method.getAnnotation(SubscribeEventWrapper.class);
        readable = "@SubscribeEvent: " + target + " " + method.getName() + getMethodDescriptor(method);
    }

    @Override
    public void invoke(EventWrapper event)
    {
        if (handler != null)
        {
            // The cast is safe because the check is removed if the event is not cancellable
            if (subInfo.receiveCanceled() || !event.isCanceled()) {
                handler.invoke(event);
            }
        }
    }

    public EventPriority getPriority()
    {
        return subInfo.priority();
    }

    public String toString()
    {
        return readable;
    }

    @Override
    public EventListener getWithoutCheck() {
        return handler;
    }
}
