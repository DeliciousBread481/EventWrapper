package io.github.lounode.eventwrapper.forge.event.converter.entity.player;

import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownFinishEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import io.github.lounode.eventwrapper.forge.event.entity.player.ItemCooldownFinishEvent;

public class ItemCooldownFinishEventConverter implements ForgeEventConverter<ItemCooldownFinishEvent, ItemCooldownFinishEventWrapper> {
    @Override
    public ItemCooldownFinishEventWrapper toWrapper(ItemCooldownFinishEvent event) {
        return new ItemCooldownFinishEventWrapper(event.getEntity(), event.getItem());
    }

    @Override
    public ItemCooldownFinishEvent toEvent(ItemCooldownFinishEventWrapper wrapper) {
        return new ItemCooldownFinishEvent(wrapper.getEntity(), wrapper.getItem());
    }
}