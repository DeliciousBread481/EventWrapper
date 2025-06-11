package io.github.lounode.eventwrapper.forge.event.converter.server;

import net.minecraftforge.event.server.ServerAboutToStartEvent;


import io.github.lounode.eventwrapper.event.server.ServerAboutToStartEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

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
