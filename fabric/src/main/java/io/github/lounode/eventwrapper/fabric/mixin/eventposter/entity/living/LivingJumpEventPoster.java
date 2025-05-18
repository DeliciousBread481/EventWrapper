package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;

@Mixin(LivingEntity.class)
public class LivingJumpEventPoster {

	@Inject(
		method = "jumpFromGround",
		at = @At("RETURN")
	)
	private void onLivingJump(CallbackInfo ci) {
		LivingEventWrapper.LivingJumpEvent event = new LivingEventWrapper.LivingJumpEvent((LivingEntity) (Object) this);
		EventsWrapper.post(event);
	}
}
