package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class PlayerInteractEventEntityInteractSpecificEventPoster2 {

    @Shadow
    private GameType localPlayerMode;

    @Inject(
            method = "interactAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V",
                    shift = At.Shift.AFTER

            ),
            cancellable = true)
    private void onInteract(Player player, Entity target, EntityHitResult ray, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.localPlayerMode == GameType.SPECTATOR) {
            cir.setReturnValue(InteractionResult.PASS);
        }
        InteractionResult cancelResult = EventWrapperHooks.onInteractEntityAt(player, target, ray, hand);
        if(cancelResult != null) {
            cir.setReturnValue(cancelResult);
        }
    }
}
