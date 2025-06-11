package io.github.lounode.eventwrapper.forge.event;

import org.jetbrains.annotations.Nullable;


import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;

public interface ForgeEventExtension {
	void EventWrapper_setEventWrapper(EventWrapper wrapper);

	@Nullable
	EventWrapper EventWrapper_getEventWrapper();
}
