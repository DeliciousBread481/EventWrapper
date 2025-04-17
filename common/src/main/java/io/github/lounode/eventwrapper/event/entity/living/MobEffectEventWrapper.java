package io.github.lounode.eventwrapper.event.entity.living;

import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This event is fired when an interaction between a {@link LivingEntity} and {@link MobEffectInstance} happens.
 * <p>
 * Wrapper for Forge Event
 */
public class MobEffectEventWrapper extends LivingEventWrapper {
    @Nullable
    protected final MobEffectInstance effectInstance;

    public MobEffectEventWrapper(LivingEntity living, MobEffectInstance effectInstance)
    {
        super(living);
        this.effectInstance = effectInstance;
    }

    public MobEffectEventWrapper(MobEffectEvent event) {
        this(event.getEntity(), event.getEffectInstance());
    }

    @Nullable
    public MobEffectInstance getEffectInstance()
    {
        return effectInstance;
    }

    public static Class<? extends Event> getForgeClass() {
        return MobEffectEvent.class;
    }

    @Override
    public Object toForgeEvent() {
        return new MobEffectEvent(getEntity(), getEffectInstance());
    }

    /**
     * This Event is fired when a {@link MobEffect} is about to get removed from an Entity.
     * This Event is {@link Cancelable}. If canceled, the effect will not be removed.
     * This Event does not have a result.
     */
    @Cancelable
    public static class Remove extends MobEffectEventWrapper
    {
        private final MobEffect effect;

        public Remove(LivingEntity living, MobEffect effect)
        {
            super(living, living.getEffect(effect));
            this.effect = effect;
        }

        public Remove(LivingEntity living, MobEffectInstance effectInstance)
        {
            super(living, effectInstance);
            this.effect = effectInstance.getEffect();
        }

        public Remove(MobEffectEvent.Remove event) {
            this(event.getEntity(), event.getEffect());
        }

        /**
         * @return the {@link MobEffectEventWrapper} which is being removed from the entity
         */
        public MobEffect getEffect()
        {
            return this.effect;
        }

        /**
         * @return the {@link MobEffectInstance}. In the remove event, this can be null if the entity does not have a {@link MobEffect} of the right type active.
         */
        @Override
        @Nullable
        public MobEffectInstance getEffectInstance()
        {
            return super.getEffectInstance();
        }

        public static Class<? extends Event> getForgeClass() {
            return MobEffectEvent.Remove.class;
        }

        @Override
        public Object toForgeEvent() {
            return new MobEffectEvent.Remove(getEntity(), getEffectInstance());
        }
    }

    /**
     * This event is fired to check if a {@link MobEffectInstance} can be applied to an entity.
     * This event is not {@link Cancelable}.
     * This event {@link HasResult has a result}.
     * <p>
     * {@link Result#ALLOW ALLOW} will apply this mob effect.
     * {@link Result#DENY DENY} will not apply this mob effect.
     * {@link Result#DEFAULT DEFAULT} will run vanilla logic to determine if this mob effect is applicable in {@link LivingEntity#canBeAffected}.
     */
    @HasResult
    public static class Applicable extends MobEffectEventWrapper
    {
        public Applicable(LivingEntity living, @NotNull MobEffectInstance effectInstance)
        {
            super(living, effectInstance);
        }

        public Applicable(MobEffectEvent.Applicable event) {
            this(event.getEntity(), event.getEffectInstance());
        }

        @Override
        @NotNull
        public MobEffectInstance getEffectInstance()
        {
            return super.getEffectInstance();
        }

        public static Class<? extends Event> getForgeClass() {
            return MobEffectEvent.Applicable.class;
        }

        @Override
        public Object toForgeEvent() {
            return new MobEffectEvent.Applicable(getEntity(), getEffectInstance());
        }
    }

    /**
     * This event is fired when a new {@link MobEffectInstance} is added to an entity.
     * This event is also fired if an entity already has the effect but with a different duration or amplifier.
     * This event is not {@link Cancelable}.
     * This event does not have a result.
     */
    public static class Added extends MobEffectEventWrapper
    {
        private final MobEffectInstance oldEffectInstance;
        private final Entity source;

        public Added(LivingEntity living, MobEffectInstance oldEffectInstance, MobEffectInstance newEffectInstance, Entity source)
        {
            super(living, newEffectInstance);
            this.oldEffectInstance = oldEffectInstance;
            this.source = source;
        }

        public Added(MobEffectEvent.Added event) {
            this(event.getEntity(), event.getOldEffectInstance(), event.getEffectInstance(), event.getEffectSource());
        }

        /**
         * @return the added {@link MobEffectInstance}. This is the unmerged MobEffectInstance if the old MobEffectInstance is not null.
         */
        @Override
        @NotNull
        public MobEffectInstance getEffectInstance()
        {
            return super.getEffectInstance();
        }

        /**
         * @return the old {@link MobEffectInstance}. This can be null if the entity did not have an effect of this kind before.
         */
        @Nullable
        public MobEffectInstance getOldEffectInstance()
        {
            return oldEffectInstance;
        }

        /**
         * @return the entity source of the effect, or {@code null} if none exists
         */
        @Nullable
        public Entity getEffectSource()
        {
            return source;
        }

        public static Class<? extends Event> getForgeClass() {
            return MobEffectEvent.Added.class;
        }

        @Override
        public Object toForgeEvent() {
            return new MobEffectEvent.Added(getEntity(), getOldEffectInstance(), getEffectInstance(), getEffectSource());
        }
    }

    /**
     * This event is fired when a {@link MobEffectInstance} expires on an entity.
     * This event is not {@link Cancelable}.
     * This event does not have a result.
     */
    public static class Expired extends MobEffectEventWrapper
    {
        public Expired(LivingEntity living, MobEffectInstance effectInstance)
        {
            super(living, effectInstance);
        }

        public Expired(MobEffectEvent.Expired event) {
            this(event.getEntity(), event.getEffectInstance());
        }

        public static Class<? extends Event> getForgeClass() {
            return MobEffectEvent.Expired.class;
        }

        @Override
        public Object toForgeEvent() {
            return new MobEffectEvent.Expired(getEntity(), getEffectInstance());
        }
    }
}
