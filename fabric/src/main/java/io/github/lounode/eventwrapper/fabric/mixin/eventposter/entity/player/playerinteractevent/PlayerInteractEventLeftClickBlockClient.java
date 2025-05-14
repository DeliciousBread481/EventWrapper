package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.lounode.eventwrapper.event.entity.player.PlayerInteractEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class PlayerInteractEventLeftClickBlockClient {

    @Shadow @Final private Minecraft minecraft;

    @WrapWithCondition(
            method = "method_41936",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z")
            )
    private boolean startDestroyBlock1(MultiPlayerGameMode instance, BlockPos flag, @Local(argsOnly = true) BlockPos loc, @Local(argsOnly = true) Direction face) {
        if (!EventWrapperHooks.onLeftClickBlock(this.minecraft.player, loc, face, ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK).isCanceled())
        {
            return true;
        }
        return false;
    }



    @Inject(
            method = "method_41930",
            at = @At("HEAD")
    )
    private void startDestroyBlock2GetEvent(BlockState blockState, BlockPos blockPos, Direction direction, int i, CallbackInfoReturnable<ServerboundPlayerActionPacket> cir, @Share("event") LocalRef<PlayerInteractEventWrapper.LeftClickBlock> event, @Local(argsOnly = true) BlockPos loc, @Local(argsOnly = true) Direction face) {
        event.set(EventWrapperHooks.onLeftClickBlock(this.minecraft.player, loc, face, ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK));
    }

    @ModifyExpressionValue(
            method = "method_41930",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyProgress:F", ordinal = 0)
    )
    private float startDestroyBlock2Attack(float original, @Share("event") LocalRef<PlayerInteractEventWrapper.LeftClickBlock> event) {
        if (event.get().getUseBlock() != EventWrapper.Result.DENY) {
            return 1;
        }
        return original;
    }

    @Inject(
            method = "method_41930",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroyProgress(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"
            ),
            cancellable = true)
    private void startDestroyBlock2Packet(BlockState blockState, BlockPos blockPos, Direction direction, int i, CallbackInfoReturnable<ServerboundPlayerActionPacket> cir, @Share("event") LocalRef<PlayerInteractEventWrapper.LeftClickBlock> event, @Local(argsOnly = true) BlockPos loc, @Local(argsOnly = true) Direction face) {
        ServerboundPlayerActionPacket packet =
                new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, loc, face, i);
        if (event.get().getUseItem() == EventWrapper.Result.DENY) {
            cir.setReturnValue(packet);
        }
    }

    @WrapWithCondition(
            method = "method_41935",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z")
            )
    private boolean startDestroyBlock3(MultiPlayerGameMode instance, BlockPos flag, @Local(argsOnly = true) BlockPos loc, @Local(argsOnly = true) Direction face) {
        if (!EventWrapperHooks.onLeftClickBlock(this.minecraft.player, loc, face, ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK).isCanceled())
        {
            return true;
        }
        return false;
    }

}
