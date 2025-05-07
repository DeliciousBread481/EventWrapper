package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class PlayerInteractEventRightClickBlockEventPosterClient {

    @Inject(
            method = "performUseItemOn",
            at = @At("HEAD"),
            cancellable = true)
    private void onUseOn(LocalPlayer player, InteractionHand hand, BlockHitResult result, CallbackInfoReturnable<InteractionResult> cir) {
        var event = EventWrapperHooks.onRightClickBlock(player, hand, result.getBlockPos(), result);
        if (event.isCanceled()) {
            cir.setReturnValue(event.getCancellationResult());
        }
    }
}
