package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(targets = "net.minecraft.server.network.ServerGamePacketListenerImpl$1")
public abstract class PlayerInteractEventEntityInteractSpecificEventPoster implements ServerboundInteractPacket.Handler {

	@Shadow
	@Final
	private ServerGamePacketListenerImpl field_28963;

	@Shadow
	@Final
	private Entity val$target;

	@Inject(
		method = "onInteraction(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)V",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	private void onInteract(InteractionHand hand, Vec3 interactionLocation, CallbackInfo ci) {
		Player player = this.field_28963.getPlayer();

		InteractionResult result = EventWrapperHooks.onInteractEntityAt(player, this.val$target, interactionLocation, hand);

		if (result != null) {
			ci.cancel();
		}
	}
}
