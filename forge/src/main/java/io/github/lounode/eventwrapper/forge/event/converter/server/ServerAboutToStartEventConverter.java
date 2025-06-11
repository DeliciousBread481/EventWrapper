package io.github.lounode.eventwrapper.forge.event.converter.server;

import io.github.lounode.eventwrapper.event.server.ServerAboutToStartEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.server.ServerAboutToStartEvent;

public class ServerAboutToStartEventConverter implements ForgeEventConverter<ServerAboutToStartEvent, ServerAboutToStartEventWrapper> {
	@Override
	public ServerAboutToStartEventWrapper toWrapper(ServerAboutToStartEvent event) {
		return new ServerAboutToStartEventWrapper(event.getServer());
	}

	@Override
	public ServerAboutToStartEvent toEvent(ServerAboutToStartEventWrapper wrapper) {
		return new ServerAboutToStartEvent(wrapper.getServer());
	}
}
