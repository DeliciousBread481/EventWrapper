package io.github.lounode.eventwrapper.forge.event.converter.server;

import io.github.lounode.eventwrapper.event.server.ServerStartedEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.server.ServerStartedEvent;

public class ServerStartedEventConverter implements ForgeEventConverter<ServerStartedEvent, ServerStartedEventWrapper> {
	@Override
	public ServerStartedEventWrapper toWrapper(ServerStartedEvent event) {
		return new ServerStartedEventWrapper(event.getServer());
	}

	@Override
	public ServerStartedEvent toEvent(ServerStartedEventWrapper wrapper) {
		return new ServerStartedEvent(wrapper.getServer());
	}
}
