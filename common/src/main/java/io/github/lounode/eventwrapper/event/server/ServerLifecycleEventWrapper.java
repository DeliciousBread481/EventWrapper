package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.eventbus.api.Event;


import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.xplatform.platform.Platform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;

public class ServerLifecycleEventWrapper extends EventWrapper {

	protected final MinecraftServer server;

	public ServerLifecycleEventWrapper(MinecraftServer server) {
		this.server = server;
	}

	public MinecraftServer getServer() {
		return server;
	}

	@SupportPlatform(Platform.FORGE)
	public ServerLifecycleEventWrapper(ServerLifecycleEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerLifecycleEvent.class;
	}

	@SupportPlatform(Platform.FORGE)
	@Override
	public Object toForgeEvent() {
		return new ServerLifecycleEvent(getServer());
	}
}
