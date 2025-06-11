package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import net.minecraftforge.event.entity.living.MobEffectEvent;

public abstract class MobEffectEventConverter {

    public static class RemoveConverter implements ForgeEventConverter<MobEffectEvent.Remove, MobEffectEventWrapper.Remove> {
        @Override
        public MobEffectEventWrapper.Remove toWrapper(MobEffectEvent.Remove event) {
            var wrapper = new MobEffectEventWrapper.Remove(event.getEntity(), event.getEffect());
            wrapper.setCanceled(event.isCanceled());
            return wrapper;
        }

        @Override
        public MobEffectEvent.Remove toEvent(MobEffectEventWrapper.Remove wrapper) {
            var event = new MobEffectEvent.Remove(wrapper.getEntity(), wrapper.getEffect());
            event.setCanceled(wrapper.isCanceled());
            return event;
        }
    }

    public static class ApplicableConverter implements ForgeEventConverter<MobEffectEvent.Applicable, MobEffectEventWrapper.Applicable> {
        @Override
        public MobEffectEventWrapper.Applicable toWrapper(MobEffectEvent.Applicable event) {
            var wrapper = new MobEffectEventWrapper.Applicable(event.getEntity(), event.getEffectInstance());
            wrapper.setResult(result(event.getResult()));
            return wrapper;
        }

        @Override
        public MobEffectEvent.Applicable toEvent(MobEffectEventWrapper.Applicable wrapper) {
            var event = new MobEffectEvent.Applicable(wrapper.getEntity(), wrapper.getEffectInstance());
            event.setResult(result(wrapper.getResult()));
            return event;
        }
    }

    public static class AddedConverter implements ForgeEventConverter<MobEffectEvent.Added, MobEffectEventWrapper.Added> {
        @Override
        public MobEffectEventWrapper.Added toWrapper(MobEffectEvent.Added event) {
            return new MobEffectEventWrapper.Added(
                    event.getEntity(),
                    event.getOldEffectInstance(),
                    event.getEffectInstance(),
                    event.getEffectSource()
            );
        }

        @Override
        public MobEffectEvent.Added toEvent(MobEffectEventWrapper.Added wrapper) {
            return new MobEffectEvent.Added(
                wrapper.getEntity(),
                wrapper.getOldEffectInstance(),
                wrapper.getEffectInstance(),
                wrapper.getEffectSource()
            );
        }
    }

    public static class ExpiredConverter implements ForgeEventConverter<MobEffectEvent.Expired, MobEffectEventWrapper.Expired> {
        @Override
        public MobEffectEventWrapper.Expired toWrapper(MobEffectEvent.Expired event) {
            return new MobEffectEventWrapper.Expired(event.getEntity(), event.getEffectInstance());
        }

        @Override
        public MobEffectEvent.Expired toEvent(MobEffectEventWrapper.Expired wrapper) {
            return new MobEffectEvent.Expired(wrapper.getEntity(), wrapper.getEffectInstance());
        }
    }
}