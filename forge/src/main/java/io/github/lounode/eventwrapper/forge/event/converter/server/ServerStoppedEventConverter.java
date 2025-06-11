package io.github.lounode.eventwrapper.forge.event.converter.server;

import io.github.lounode.eventwrapper.event.server.ServerStoppedEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.server.ServerStoppedEvent;

public class ServerStoppedEventConverter implements ForgeEventConverter<ServerStoppedEvent, ServerStoppedEventWrapper> {
	@Override
	public ServerStoppedEventWrapper toWrapper(ServerStoppedEvent event) {
		return new ServerStoppedEventWrapper(event.getServer());
	}

	@Override
	public ServerStoppedEvent toEvent(ServerStoppedEventWrapper wrapper) {
		return new ServerStoppedEvent(wrapper.getServer());
	}
}
