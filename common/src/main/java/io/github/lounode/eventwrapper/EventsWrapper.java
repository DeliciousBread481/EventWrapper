package io.github.lounode.eventwrapper;


import io.github.lounode.eventwrapper.eventbus.api.BusBuilder;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.IEventBus;
import io.github.lounode.eventwrapper.eventbus.api.IPlatformEventHelper;

public class EventsWrapper {
	public static EventsWrapper INSTANCE = new EventsWrapper();
	private final IPlatformEventHelper helper = IPlatformEventHelper.INSTANCE;

	public static EventsWrapper getInstance() {
		return INSTANCE;
	}

	public static final IEventBus WRAPPER_EVENT_BUS = BusBuilder.builder().startShutdown().build();

	public static void register(Object target) {
		getInstance().helper.register(target);
	}

	public static <T extends EventWrapper> T post(T event) {
		return getInstance().helper.post(event);
	}

	public IPlatformEventHelper getHelper() {
		return helper;
	}
}
