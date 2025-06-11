package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;


import io.github.lounode.xplatform.platform.Platform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called after {@link ServerStoppingEvent} when the server has completely shut down.
 * Called immediately before shutting down, on the dedicated server, and before returning
 * to the main menu on the client.
 *
 * @author cpw
 */
public class ServerStoppedEventWrapper extends ServerLifecycleEventWrapper {
	public ServerStoppedEventWrapper(MinecraftServer server) {
		super(server);
	}

	@SupportPlatform(Platform.FORGE)
	public ServerStoppedEventWrapper(ServerStoppedEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerStoppedEvent.class;
	}

	@SupportPlatform(Platform.FORGE)
	@Override
	public Object toForgeEvent() {
		return new ServerStoppedEvent(getServer());
	}
}
