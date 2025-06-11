package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;


import io.github.lounode.xplatform.platform.Platform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called after {@link ServerStartingEvent} when the server is available and ready to play.
 *
 * @author cpw
 */
public class ServerStartedEventWrapper extends ServerLifecycleEventWrapper {

	public ServerStartedEventWrapper(final MinecraftServer server) {
		super(server);
	}

	@SupportPlatform(Platform.FORGE)
	public ServerStartedEventWrapper(ServerStartedEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerStartedEvent.class;
	}

	@SupportPlatform(Platform.FORGE)
	@Override
	public Object toForgeEvent() {
		return new ServerStartedEvent(getServer());
	}
}
