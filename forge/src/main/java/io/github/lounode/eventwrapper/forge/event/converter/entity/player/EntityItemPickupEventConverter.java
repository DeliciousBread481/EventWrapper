package io.github.lounode.eventwrapper.forge.event.converter.entity.player;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;


import io.github.lounode.eventwrapper.event.entity.player.EntityItemPickupEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class EntityItemPickupEventConverter implements ForgeEventConverter<EntityItemPickupEvent, EntityItemPickupEventWrapper> {

	@Override
	public EntityItemPickupEventWrapper toWrapper(EntityItemPickupEvent event) {
		var wrapper = new EntityItemPickupEventWrapper(event.getEntity(), event.getItem());
		wrapper.setCanceled(event.isCanceled());
		wrapper.setResult(result(event.getResult()));
		return wrapper;
	}

	@Override
	public EntityItemPickupEvent toEvent(EntityItemPickupEventWrapper wrapper) {
		var event = new EntityItemPickupEvent(wrapper.getEntity(), wrapper.getItem());
		event.setCanceled(event.isCanceled());
		event.setResult(result(wrapper.getResult()));
		return event;
	}
}
