package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import net.minecraftforge.event.entity.living.LivingDeathEvent;


import io.github.lounode.eventwrapper.event.entity.living.LivingDeathEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class LivingDeathEventConverter implements ForgeEventConverter<LivingDeathEvent, LivingDeathEventWrapper> {
	@Override
	public LivingDeathEvent toEvent(LivingDeathEventWrapper wrapper) {
		var event = new LivingDeathEvent(wrapper.getEntity(), wrapper.getSource());
		event.setCanceled(wrapper.isCanceled());
		return event;
	}

	@Override
	public LivingDeathEventWrapper toWrapper(LivingDeathEvent event) {
		var wrapper = new LivingDeathEventWrapper(event.getEntity(), event.getSource());
		wrapper.setCanceled(event.isCanceled());
		return wrapper;
	}
}
