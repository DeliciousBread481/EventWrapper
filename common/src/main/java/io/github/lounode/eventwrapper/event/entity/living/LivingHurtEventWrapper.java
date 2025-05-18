package io.github.lounode.eventwrapper.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;


import io.github.lounode.eventwrapper.eventbus.api.Cancelable;

/**
 * LivingHurtEvent is fired when an Entity is set to be hurt. <br>
 * This event is fired whenever an Entity is hurt in
 * {@code LivingEntity#actuallyHurt(DamageSource, float)} and
 * {@code Player#actuallyHurt(DamageSource, float)}.<br>
 * <br>
 * This event is fired via the {@link ForgeHooks#onLivingHurt(LivingEntity, DamageSource, float)}.<br>
 * <br>
 * {@link #source} contains the DamageSource that caused this Entity to be hurt. <br>
 * {@link #amount} contains the amount of damage dealt to the Entity that was hurt. <br>
 * <br>
 * This event is {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
 * If this event is canceled, the Entity is not hurt.<br>
 * <br>
 * This event does not have a result. {@link Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 * 
 * @see LivingDamageEvent
 **/
@Cancelable
public class LivingHurtEventWrapper extends LivingEventWrapper {

	private final DamageSource source;
	private float amount;

	public LivingHurtEventWrapper(LivingEntity entity, DamageSource source, float amount) {
		super(entity);
		this.source = source;
		this.amount = amount;
	}

	public LivingHurtEventWrapper(LivingHurtEvent event) {
		this(event.getEntity(), event.getSource(), event.getAmount());
	}

	public DamageSource getSource() {
		return source;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public static Class<? extends Event> getForgeClass() {
		return LivingHurtEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new LivingHurtEvent(getEntity(), getSource(), getAmount());
	}
}
