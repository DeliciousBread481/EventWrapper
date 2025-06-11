package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import net.minecraftforge.event.entity.living.LivingHurtEvent;


import io.github.lounode.eventwrapper.event.entity.living.LivingHurtEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class LivingHurtEventConverter implements ForgeEventConverter<LivingHurtEvent, LivingHurtEventWrapper> {
	@Override
	public LivingHurtEventWrapper toWrapper(LivingHurtEvent event) {
		var wrapper = new LivingHurtEventWrapper(event.getEntity(), event.getSource(), event.getAmount());
		wrapper.setCanceled(event.isCanceled());
		return wrapper;
	}

	@Override
	public LivingHurtEvent toEvent(LivingHurtEventWrapper wrapper) {
		var event = new LivingHurtEvent(wrapper.getEntity(), wrapper.getSource(), wrapper.getAmount());
		event.setCanceled(wrapper.isCanceled());
		return event;
	}
}
