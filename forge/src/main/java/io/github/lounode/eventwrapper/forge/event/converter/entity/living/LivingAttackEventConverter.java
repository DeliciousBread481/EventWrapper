package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.LivingAttackEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class LivingAttackEventConverter implements ForgeEventConverter<LivingAttackEvent, LivingAttackEventWrapper> {
    @Override
    public LivingAttackEventWrapper toWrapper(LivingAttackEvent event) {
        var wrapper = new LivingAttackEventWrapper(event.getEntity(), event.getSource(), event.getAmount());
        wrapper.setCanceled(event.isCanceled());
        return wrapper;
    }

    @Override
    public LivingAttackEvent toEvent(LivingAttackEventWrapper wrapper) {
        var event = new LivingAttackEvent(wrapper.getEntity(), wrapper.getSource(), wrapper.getAmount());
        event.setCanceled(wrapper.isCanceled());
        return event;
    }
}