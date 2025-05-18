package io.github.lounode.eventwrapper.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;


import io.github.lounode.eventwrapper.eventbus.api.Cancelable;

/**
 * LivingAttackEvent is fired when a living Entity is attacked. <br>
 * This event is fired whenever an Entity is attacked in
 * {@link LivingEntity#hurt(DamageSource, float)} and
 * {@link Player#hurt(DamageSource, float)}. <br>
 * <br>
 * This event is fired via the {@link ForgeHooks#onLivingAttack(LivingEntity, DamageSource, float)}.<br>
 * <br>
 * {@link #source} contains the DamageSource of the attack. <br>
 * {@link #amount} contains the amount of damage dealt to the entity. <br>
 * <br>
 * This event is {@link Cancelable}.<br>
 * If this event is canceled, the Entity does not take attack damage.<br>
 * <br>
 * This event does not have a result. {@link Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class LivingAttackEventWrapper extends LivingEventWrapper {

	private final DamageSource source;
	private final float amount;

	public LivingAttackEventWrapper(LivingEntity entity, DamageSource source, float amount) {
		super(entity);
		this.source = source;
		this.amount = amount;
	}

	public LivingAttackEventWrapper(LivingAttackEvent event) {
		this(event.getEntity(), event.getSource(), event.getAmount());
	}

	public DamageSource getSource() {
		return source;
	}

	public float getAmount() {
		return amount;
	}

	public static Class<? extends Event> getForgeClass() {
		return LivingAttackEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new LivingAttackEvent(getEntity(), getSource(), getAmount());
	}
}
