package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.Event;


import io.github.lounode.xplatform.platform.Platform;
import io.github.lounode.xplatform.platform.support.CrossPlatform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;

/**
 * Called when the server begins an orderly shutdown, before {@link ServerStoppedEvent}.
 *
 * @author cpw
 */
@CrossPlatform
public class ServerStoppingEventWrapper extends ServerLifecycleEventWrapper {
	public ServerStoppingEventWrapper(MinecraftServer server) {
		super(server);
	}

	@SupportPlatform(Platform.FORGE)
	public ServerStoppingEventWrapper(ServerStoppingEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerStoppingEvent.class;
	}

	@SupportPlatform(Platform.FORGE)
	@Override
	public Object toForgeEvent() {
		return new ServerStoppingEvent(getServer());
	}
}
