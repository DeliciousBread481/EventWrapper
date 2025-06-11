package io.github.lounode.eventwrapper.forge.event.converter.server;

import io.github.lounode.eventwrapper.event.server.ServerStartingEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.server.ServerStartingEvent;

public class ServerStartingEventConverter implements ForgeEventConverter<ServerStartingEvent, ServerStartingEventWrapper> {
	@Override
	public ServerStartingEventWrapper toWrapper(ServerStartingEvent event) {
		return new ServerStartingEventWrapper(event.getServer());
	}

	@Override
	public ServerStartingEvent toEvent(ServerStartingEventWrapper wrapper) {
		return new ServerStartingEvent(wrapper.getServer());
	}
}
