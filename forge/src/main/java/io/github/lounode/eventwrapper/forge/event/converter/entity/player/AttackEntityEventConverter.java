package io.github.lounode.eventwrapper.forge.event.converter.entity.player;

import net.minecraftforge.event.entity.player.AttackEntityEvent;


import io.github.lounode.eventwrapper.event.entity.player.AttackEntityEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class AttackEntityEventConverter implements ForgeEventConverter<AttackEntityEvent, AttackEntityEventWrapper> {
	@Override
	public AttackEntityEventWrapper toWrapper(AttackEntityEvent event) {
		var wrapper = new AttackEntityEventWrapper(event.getEntity(), event.getTarget());
		wrapper.setCanceled(event.isCanceled());
		return wrapper;
	}

	@Override
	public AttackEntityEvent toEvent(AttackEntityEventWrapper wrapper) {
		var event = new AttackEntityEvent(wrapper.getEntity(), wrapper.getTarget());
		event.setCanceled(wrapper.isCanceled());
		return event;
	}
}
