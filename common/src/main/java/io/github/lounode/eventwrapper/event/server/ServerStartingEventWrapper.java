package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;


import io.github.lounode.xplatform.platform.Platform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called after {@link ServerAboutToStartEvent} and before {@link ServerStartedEvent}.
 * This event allows for customizations of the server.
 * <p>
 * If you need to add commands use {@link net.minecraftforge.event.RegisterCommandsEvent}.
 *
 * @author cpw
 */
public class ServerStartingEventWrapper extends ServerLifecycleEventWrapper {
	public ServerStartingEventWrapper(final MinecraftServer server) {
		super(server);
	}

	@SupportPlatform(Platform.FORGE)
	public ServerStartingEventWrapper(ServerStartingEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerStartingEvent.class;
	}

	@SupportPlatform(Platform.FORGE)
	@Override
	public Object toForgeEvent() {
		return new ServerStartingEvent(getServer());
	}
}
