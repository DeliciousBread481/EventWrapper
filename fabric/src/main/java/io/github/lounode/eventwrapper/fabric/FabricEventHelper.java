package io.github.lounode.eventwrapper.fabric;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.IPlatformEventHelper;

public class FabricEventHelper implements IPlatformEventHelper {
	@Override
	public void register(Object target) {
		EventsWrapper.WRAPPER_EVENT_BUS.register(target);
	}

	@Override
	public void unregister(Object target) {
		EventsWrapper.WRAPPER_EVENT_BUS.unregister(target);
	}

	@Override
	public boolean isFabric() {
		return true;
	}

	@Override
	public <T extends EventWrapper> T post(T event) {
		return EventsWrapper.WRAPPER_EVENT_BUS.post(event);
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState state, Player player) {
		return EventWrapperHooks.isCorrectToolForDrops(state, player);
	}
}
