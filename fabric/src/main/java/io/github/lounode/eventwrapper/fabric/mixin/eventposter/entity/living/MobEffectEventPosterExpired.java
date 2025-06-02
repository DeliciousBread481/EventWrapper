package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(LivingEntity.class)
public class MobEffectEventPosterExpired {
	@Inject(
		method = "tickEffects",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Iterator;remove()V"
		)
	)
	private void onExpired(CallbackInfo ci, @Local MobEffectInstance mobEffect) {
		EventWrapperHooks.onMobEffectExpired((LivingEntity) (Object) this, mobEffect);
	}
}
