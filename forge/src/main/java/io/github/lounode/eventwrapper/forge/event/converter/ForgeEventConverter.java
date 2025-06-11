package io.github.lounode.eventwrapper.forge.event.converter;

import net.minecraftforge.eventbus.api.Event;

import org.jetbrains.annotations.Nullable;


import io.github.lounode.eventwrapper.eventbus.api.EventConverter;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;

public interface ForgeEventConverter<E extends Event, W extends EventWrapper> extends EventConverter<E, W> {
	@Override
	E toEvent(W wrapper);

	@Override
	W toWrapper(E event);

	default net.minecraftforge.eventbus.api.EventPriority phase(@Nullable io.github.lounode.eventwrapper.eventbus.api.EventPriority phase) {

		return net.minecraftforge.eventbus.api.EventPriority.valueOf(phase.name());
	}

	default io.github.lounode.eventwrapper.eventbus.api.EventPriority phase(@Nullable net.minecraftforge.eventbus.api.EventPriority phase) {
		return io.github.lounode.eventwrapper.eventbus.api.EventPriority.valueOf(phase.name());
	}

	default Event.Result result(EventWrapper.Result result) {
		return Event.Result.valueOf(result.name());
	}

	default EventWrapper.Result result(Event.Result result) {
		return EventWrapper.Result.valueOf(result.name());
	}
}
