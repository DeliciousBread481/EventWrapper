package io.github.lounode.eventwrapper.fabric;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.IPlatformEventHelper;

public class FabricEventHelper implements IPlatformEventHelper {
	@Override
	public void register(Object target) {
		EventsWrapper.WRAPPER_EVENT_BUS.register(target);
	}

	@Override
	public boolean isFabric() {
		return true;
	}

	@Override
	public <T extends EventWrapper> T post(T event) {
		return EventsWrapper.WRAPPER_EVENT_BUS.post(event);
	}
}
