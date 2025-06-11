package io.github.lounode.eventwrapper.forge.event.converter.entity.player;


import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownStartEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import io.github.lounode.eventwrapper.forge.event.entity.player.ItemCooldownStartEvent;

public class ItemCooldownStartEventConverter implements ForgeEventConverter<ItemCooldownStartEvent, ItemCooldownStartEventWrapper> {
	@Override
	public ItemCooldownStartEventWrapper toWrapper(ItemCooldownStartEvent event) {
		return new ItemCooldownStartEventWrapper(event.getEntity(), event.getItem(), event.getTicks());
	}

	@Override
	public ItemCooldownStartEvent toEvent(ItemCooldownStartEventWrapper wrapper) {
		return new ItemCooldownStartEvent(wrapper.getEntity(), wrapper.getItem(), wrapper.getTicks());
	}
}
