package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;

@Mixin(MobEffectInstance.class)
public class MobEffectEventPosterExpired {
	@Inject(
		method = "tick",
		at = @At("TAIL")
	)
	private void onTick(LivingEntity entity, Runnable onExpirationRunnable, CallbackInfoReturnable<Boolean> cir) {
		EventsWrapper.post(new MobEffectEventWrapper.Expired(entity, (MobEffectInstance) (Object) this));
	}
}
