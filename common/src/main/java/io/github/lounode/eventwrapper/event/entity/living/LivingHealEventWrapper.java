package io.github.lounode.eventwrapper.event.entity.living;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * LivingHealEvent is fired when an Entity is set to be healed. <br>
 * This event is fired whenever an Entity is healed in {@link LivingEntity#heal(float)}<br>
 * <br>
 * This event is fired via the {@link ForgeEventFactory#onLivingHeal(LivingEntity, float)}.<br>
 * <br>
 * {@link #amount} contains the amount of healing done to the Entity that was healed. <br>
 * <br>
 * This event is {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
 * If this event is canceled, the Entity is not healed.<br>
 * <br>
 * This event does not have a result. {@link Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class LivingHealEventWrapper extends LivingEventWrapper {
    private float amount;

    public LivingHealEventWrapper(LivingEntity livingEntity, float amount) {
        super(livingEntity);
        this.amount = amount;
    }

    public LivingHealEventWrapper(LivingHealEvent event) {
        this(event.getEntity(), event.getAmount());
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public static Class<? extends Event> getForgeClass() {
        return LivingHealEvent.class;
    }

    @Override
    public Object toForgeEvent() {
        return new LivingHealEvent(getEntity(), getAmount());
    }
}
