package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living.living_death_event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(Player.class)
public class EventPosterPlayer {

	@Inject(
		method = "die",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onPlayerDie(DamageSource damageSource, CallbackInfo ci) {
		boolean cancel = EventWrapperHooks.onLivingDeath((Player) (Object) this, damageSource);
		if (cancel) {
			ci.cancel();
		}
	}
}
