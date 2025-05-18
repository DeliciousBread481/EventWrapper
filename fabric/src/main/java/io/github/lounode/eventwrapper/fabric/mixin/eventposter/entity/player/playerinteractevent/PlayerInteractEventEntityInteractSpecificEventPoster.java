package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(targets = "net.minecraft.server.network.ServerGamePacketListenerImpl$1")
public abstract class PlayerInteractEventEntityInteractSpecificEventPoster {

	@Shadow
	protected abstract void performInteraction(InteractionHand hand, ServerGamePacketListenerImpl.EntityInteraction interaction);

	@Inject(
		method = "onInteraction(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)V",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	private void onInteract(InteractionHand hand, Vec3 interactionLocation, CallbackInfo ci) {
		ci.cancel();
		this.performInteraction(hand, ((serverPlayer, entity, interactionHand) -> {
			InteractionResult result = EventWrapperHooks.onInteractEntityAt(serverPlayer, entity, interactionLocation, interactionHand);
			if (result != null) {
				return result;
			}
			return entity.interactAt(serverPlayer, interactionLocation, interactionHand);
		}));
	}
}
