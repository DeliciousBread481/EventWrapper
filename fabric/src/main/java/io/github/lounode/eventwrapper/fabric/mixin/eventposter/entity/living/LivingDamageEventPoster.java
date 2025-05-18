package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingDamageEventWrapper;

@Mixin(LivingEntity.class)
public class LivingDamageEventPoster {

	@ModifyVariable(
		method = "actuallyHurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;setAbsorptionAmount(F)V",
			shift = At.Shift.AFTER
		),
		argsOnly = true,
		ordinal = 0
	)
	private float modifyIncomingDamage(float originalDamage, DamageSource damageSource) {
		LivingDamageEventWrapper event = new LivingDamageEventWrapper((LivingEntity) (Object) this, damageSource, originalDamage);

		EventsWrapper.post(event);

		if (event.isCanceled()) {
			return 0;
		}

		return event.getAmount();
	}
}
