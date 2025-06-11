package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.LivingHealEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class LivingHealEventConverter implements ForgeEventConverter<LivingHealEvent, LivingHealEventWrapper> {
    @Override
    public LivingHealEventWrapper toWrapper(LivingHealEvent event) {
        var wrapper = new LivingHealEventWrapper(event.getEntity(), event.getAmount());
        wrapper.setCanceled(event.isCanceled());
        return wrapper;
    }

    @Override
    public LivingHealEvent toEvent(LivingHealEventWrapper wrapper) {
        var event = new LivingHealEvent(wrapper.getEntity(), wrapper.getAmount());
        event.setCanceled(wrapper.isCanceled());
        return event;
    }
}