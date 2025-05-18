package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the server begins an orderly shutdown, before {@link ServerStoppedEvent}.
 *
 * @author cpw
 */
public class ServerStoppingEventWrapper extends ServerLifecycleEventWrapper {
	public ServerStoppingEventWrapper(MinecraftServer server) {
		super(server);
	}

	public ServerStoppingEventWrapper(ServerStoppingEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerStoppingEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new ServerStoppingEvent(getServer());
	}
}
