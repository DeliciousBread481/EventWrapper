package io.github.lounode.eventwrapper.forge.event.converter.server;

import net.minecraftforge.event.server.ServerLifecycleEvent;


import io.github.lounode.eventwrapper.event.server.ServerLifecycleEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class ServerLifecycleEventConverter implements ForgeEventConverter<ServerLifecycleEvent, ServerLifecycleEventWrapper> {

	@Override
	public ServerLifecycleEventWrapper toWrapper(ServerLifecycleEvent event) {
		return new ServerLifecycleEventWrapper(event.getServer());
	}

	@Override
	public ServerLifecycleEvent toEvent(ServerLifecycleEventWrapper wrapper) {
		return new ServerLifecycleEvent(wrapper.getServer());
	}
}
