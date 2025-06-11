package io.github.lounode.eventwrapper.forge.event;

import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import org.jetbrains.annotations.Nullable;

public interface ForgeEventExtension {
    void EventWrapper_setEventWrapper(EventWrapper wrapper);

    @Nullable
    EventWrapper EventWrapper_getEventWrapper();
}
