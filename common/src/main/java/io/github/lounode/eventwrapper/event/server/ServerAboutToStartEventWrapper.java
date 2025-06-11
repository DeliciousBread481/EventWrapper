package io.github.lounode.eventwrapper.event.server;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;


import io.github.lounode.xplatform.platform.Platform;
import io.github.lounode.xplatform.platform.support.SupportPlatform;

/**
 * Called before the server begins loading anything. Called after {@link InterModProcessEvent} on the dedicated
 * server, and after the player has hit "Play Selected World" in the client. Called before {@link ServerStartingEvent}.
 *
 * You can obtain a reference to the server with this event.
 * 
 * @author cpw
 */
public class ServerAboutToStartEventWrapper extends ServerLifecycleEventWrapper {

	public ServerAboutToStartEventWrapper(MinecraftServer server) {
		super(server);
	}

	@SupportPlatform(Platform.FORGE)
	public ServerAboutToStartEventWrapper(ServerAboutToStartEvent event) {
		this(event.getServer());
	}

	public static Class<? extends Event> getForgeClass() {
		return ServerAboutToStartEvent.class;
	}

	@SupportPlatform(Platform.FORGE)
	@Override
	public Object toForgeEvent() {
		return new ServerAboutToStartEvent(getServer());
	}
}
