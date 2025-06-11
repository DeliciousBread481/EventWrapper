package io.github.lounode.eventwrapper.forge.event.converter.furnace;

import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;


import io.github.lounode.eventwrapper.event.furnace.FurnaceFuelBurnTimeEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class FurnaceFuelBurnTimeEventConverter implements ForgeEventConverter<FurnaceFuelBurnTimeEvent, FurnaceFuelBurnTimeEventWrapper> {
	@Override
	public FurnaceFuelBurnTimeEventWrapper toWrapper(FurnaceFuelBurnTimeEvent event) {
		var wrapper = new FurnaceFuelBurnTimeEventWrapper(
				event.getItemStack(),
				event.getBurnTime(),
				event.getRecipeType()
		);
		wrapper.setCanceled(event.isCanceled());
		return wrapper;
	}

	@Override
	public FurnaceFuelBurnTimeEvent toEvent(FurnaceFuelBurnTimeEventWrapper wrapper) {
		var event = new FurnaceFuelBurnTimeEvent(
				wrapper.getItemStack(),
				wrapper.getBurnTime(),
				wrapper.getRecipeType()
		);
		event.setCanceled(wrapper.isCanceled());
		return event;
	}
}
