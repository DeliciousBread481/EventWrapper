package io.github.lounode.eventwrapper.forge.event.converter.entity.player;

import net.minecraftforge.event.entity.player.AnvilRepairEvent;


import io.github.lounode.eventwrapper.event.entity.player.AnvilRepairEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class AnvilRepairEventConverter implements ForgeEventConverter<AnvilRepairEvent, AnvilRepairEventWrapper> {
	@Override
	public AnvilRepairEventWrapper toWrapper(AnvilRepairEvent event) {
		AnvilRepairEventWrapper wrapper = new AnvilRepairEventWrapper(
				event.getEntity(),
				event.getLeft(),
				event.getRight(),
				event.getOutput()
		);
		wrapper.setBreakChance(event.getBreakChance());
		return wrapper;
	}

	@Override
	public AnvilRepairEvent toEvent(AnvilRepairEventWrapper wrapper) {
		AnvilRepairEvent event = new AnvilRepairEvent(
				wrapper.getEntity(),
				wrapper.getLeft(),
				wrapper.getRight(),
				wrapper.getOutput()
		);
		event.setBreakChance(wrapper.getBreakChance());
		return event;
	}
}
