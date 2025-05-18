package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
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

	public ServerStartedEventWrapper(ServerStartedEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerStartedEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new ServerStartedEvent(getServer());
	}
}
