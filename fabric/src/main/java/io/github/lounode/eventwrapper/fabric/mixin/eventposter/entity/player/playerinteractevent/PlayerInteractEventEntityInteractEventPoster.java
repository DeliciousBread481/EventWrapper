package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(Player.class)
public class PlayerInteractEventEntityInteractEventPoster {

	@Inject(
		method = "interactOn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"
		),
		cancellable = true
	)
	private void onInteract(Entity entityToInteractOn, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		InteractionResult result = EventWrapperHooks.onInteractEntity((Player) (Object) this, entityToInteractOn, hand);
		if (result != null) {
			cir.setReturnValue(result);
		}
	}
}
