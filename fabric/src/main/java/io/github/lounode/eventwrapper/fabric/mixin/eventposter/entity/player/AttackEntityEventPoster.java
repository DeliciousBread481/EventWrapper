package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.AttackEntityEventWrapper;

@Mixin(Player.class)
public class AttackEntityEventPoster {
	@Inject(
		method = "attack",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onPlayerAttack(Entity target, CallbackInfo ci) {
		AttackEntityEventWrapper wrapper = new AttackEntityEventWrapper((Player) (Object) this, target);

		EventsWrapper.post(wrapper);

		if (wrapper.isCanceled()) {
			ci.cancel();
		}
	}
}
