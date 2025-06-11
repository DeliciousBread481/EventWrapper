package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent;

public abstract class LivingEventConverter {

    public static class LivingTickConverter implements ForgeEventConverter<LivingEvent.LivingTickEvent, LivingEventWrapper.LivingTickEvent> {
        @Override
        public LivingEventWrapper.LivingTickEvent toWrapper(LivingEvent.LivingTickEvent event) {
            var wrapper = new LivingEventWrapper.LivingTickEvent(event.getEntity());
            wrapper.setCanceled(event.isCanceled());
            return wrapper;
        }

        @Override
        public LivingEvent.LivingTickEvent toEvent(LivingEventWrapper.LivingTickEvent wrapper) {
            var event = new LivingEvent.LivingTickEvent(wrapper.getEntity());
            event.setCanceled(wrapper.isCanceled());
            return event;
        }
    }

    public static class LivingJumpConverter implements ForgeEventConverter<LivingEvent.LivingJumpEvent, LivingEventWrapper.LivingJumpEvent> {
        @Override
        public LivingEventWrapper.LivingJumpEvent toWrapper(LivingEvent.LivingJumpEvent event) {
            return new LivingEventWrapper.LivingJumpEvent(event.getEntity());
        }

        @Override
        public LivingEvent.LivingJumpEvent toEvent(LivingEventWrapper.LivingJumpEvent wrapper) {
            return new LivingEvent.LivingJumpEvent(wrapper.getEntity());
        }
    }

    public static class LivingVisibilityConverter implements ForgeEventConverter<LivingEvent.LivingVisibilityEvent, LivingEventWrapper.LivingVisibilityEvent> {
        @Override
        public LivingEventWrapper.LivingVisibilityEvent toWrapper(LivingEvent.LivingVisibilityEvent event) {
            var wrapper = new LivingEventWrapper.LivingVisibilityEvent(
                event.getEntity(),
                event.getLookingEntity(),
                event.getVisibilityModifier()
            );
            return wrapper;
        }

        @Override
        public LivingEvent.LivingVisibilityEvent toEvent(LivingEventWrapper.LivingVisibilityEvent wrapper) {
            var event = new LivingEvent.LivingVisibilityEvent(
                wrapper.getEntity(),
                wrapper.getLookingEntity(),
                wrapper.getVisibilityModifier()
            );
            return event;
        }
    }
}