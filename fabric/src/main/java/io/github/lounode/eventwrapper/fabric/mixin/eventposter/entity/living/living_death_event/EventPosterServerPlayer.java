package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living.living_death_event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerPlayer.class)
public class EventPosterServerPlayer {

	@Inject(
		method = "die",

		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;)V",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void onServerPlayerDie(DamageSource damageSource, CallbackInfo ci) {
		boolean cancel = EventWrapperHooks.onLivingDeath((ServerPlayer) (Object) this, damageSource);
		if (cancel) {
			ci.cancel();
		}
	}
}
