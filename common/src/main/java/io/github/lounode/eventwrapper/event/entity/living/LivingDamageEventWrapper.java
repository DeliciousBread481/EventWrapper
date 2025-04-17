package io.github.lounode.eventwrapper.event.entity.living;

import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * LivingDamageEvent is fired just before damage is applied to entity.<br>
 * At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.<br>
 * Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.<br>
 * This event is fired whenever an Entity is damaged in
 * {@code LivingEntity#actuallyHurt(DamageSource, float)} and
 * {@code Player#actuallyHurt(DamageSource, float)}.<br>
 * <br>
 * This event is fired via the {@link ForgeHooks#onLivingDamage(LivingEntity, DamageSource, float)}.<br>
 * <br>
 * {@link #source} contains the DamageSource that caused this Entity to be hurt. <br>
 * {@link #amount} contains the final amount of damage that will be dealt to entity. <br>
 * <br>
 * This event is {@link Cancelable}.<br>
 * If this event is canceled, the Entity is not hurt. Used resources WILL NOT be restored.<br>
 * <br>
 * This event does not have a result. {@link Event.HasResult}<br>
 * @see LivingHurtEvent
 **/
@Cancelable
public class LivingDamageEventWrapper extends LivingEventWrapper{

    private final DamageSource source;
    private float amount;
    public LivingDamageEventWrapper(LivingEntity entity, DamageSource source, float amount)
    {
        super(entity);
        this.source = source;
        this.amount = amount;
    }

    public LivingDamageEventWrapper(LivingDamageEvent event) {
        this(event.getEntity(), event.getSource(), event.getAmount());
    }

    public DamageSource getSource() { return source; }

    public float getAmount() { return amount; }

    public void setAmount(float amount) { this.amount = amount; }

    public static Class<? extends Event> getForgeClass() {
        return LivingDamageEvent.class;
    }

    @Override
    public Object toForgeEvent() {
        return new LivingDamageEvent(getEntity(), getSource(), getAmount());
    }
}
