package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living.shield_block_event;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.event.entity.living.ShieldBlockEventWrapper;
import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(LivingEntity.class)
public class EventPosterLivingEntity {

	@Inject(
		method = "hurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;isDamageSourceBlocked(Lnet/minecraft/world/damagesource/DamageSource;)Z",
			shift = At.Shift.AFTER
		)
	)
	private void onAfterTestBlock(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Share("wrapper") LocalRef<ShieldBlockEventWrapper> wrapper) {
		var event = EventWrapperHooks.onShieldBlock((LivingEntity) (Object) this, source, amount);
		wrapper.set(event);
	}

	@WrapWithCondition(
		method = "hurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;hurtCurrentlyUsedShield(F)V"
		)
	)
	private boolean onHurtCurrentlyUsedShield(LivingEntity instance, float damageAmount, @Share("wrapper") LocalRef<ShieldBlockEventWrapper> wrapper) {
		var event = wrapper.get();
		if (event.isCanceled() || !event.shieldTakesDamage()) {
			return false;
		}

		return true;
	}

	//BUG FEATURE: We ignore stats damage change, It's too hard and may course some issue

	@WrapWithCondition(
		method = "hurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;blockUsingShield(Lnet/minecraft/world/entity/LivingEntity;)V"
		)
	)
	private boolean onNonProjectileBlocked(LivingEntity instance, LivingEntity attacker, @Local(argsOnly = true) DamageSource source, @Share("wrapper") LocalRef<ShieldBlockEventWrapper> wrapper) {
		var event = wrapper.get();
		if (event.isCanceled()) {
			return false;
		}
		return true;
	}

	@ModifyVariable(
		method = "hurt",
		at = @At(
			value = "STORE",
			ordinal = 1
		),
		ordinal = 0
	)
	private boolean modifyBlFlag(boolean original, @Local(argsOnly = true) LocalFloatRef amount, @Share("wrapper") LocalRef<ShieldBlockEventWrapper> wrapper) {
		var event = wrapper.get();
		if (!event.isCanceled() && original) {
			float am = event.getOriginalBlockedDamage() - event.getBlockedDamage();
			amount.set(am);
			return am <= 0;
		}
		return original;
	}
}
