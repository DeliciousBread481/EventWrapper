package io.github.lounode.eventwrapper.forge.event.converter.server;

import io.github.lounode.eventwrapper.event.server.ServerStoppingEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.server.ServerStoppingEvent;

public class ServerStoppingEventConverter implements ForgeEventConverter<ServerStoppingEvent, ServerStoppingEventWrapper> {
	@Override
	public ServerStoppingEventWrapper toWrapper(ServerStoppingEvent event) {
		return new ServerStoppingEventWrapper(event.getServer());
	}

	@Override
	public ServerStoppingEvent toEvent(ServerStoppingEventWrapper wrapper) {
		return new ServerStoppingEvent(wrapper.getServer());
	}
}
