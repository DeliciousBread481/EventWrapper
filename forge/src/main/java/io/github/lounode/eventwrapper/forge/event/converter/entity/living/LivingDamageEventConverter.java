package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.LivingDamageEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class LivingDamageEventConverter implements ForgeEventConverter<LivingDamageEvent, LivingDamageEventWrapper> {
    @Override
    public LivingDamageEventWrapper toWrapper(LivingDamageEvent event) {
        var wrapper = new LivingDamageEventWrapper(event.getEntity(), event.getSource(), event.getAmount());
        wrapper.setCanceled(event.isCanceled());
        return wrapper;
    }

    @Override
    public LivingDamageEvent toEvent(LivingDamageEventWrapper wrapper) {
        var event = new LivingDamageEvent(wrapper.getEntity(), wrapper.getSource(), wrapper.getAmount());
        event.setCanceled(wrapper.isCanceled());
        return event;
    }
}