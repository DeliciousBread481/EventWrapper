package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;

@Mixin(Spider.class)
public class MobEffectEventPosterApplicable {
	@Inject(
		method = "canBeAffected",
		at = @At("HEAD"),
		cancellable = true
	)
	private void canBeAffected(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
		if (effectInstance.getEffect() == MobEffects.POISON) {
			MobEffectEventWrapper.Applicable event = new MobEffectEventWrapper.Applicable((LivingEntity) (Object) this, effectInstance);
			EventsWrapper.post(event);

			cir.setReturnValue(event.getResult() == EventWrapper.Result.ALLOW);
		}
	}
}
