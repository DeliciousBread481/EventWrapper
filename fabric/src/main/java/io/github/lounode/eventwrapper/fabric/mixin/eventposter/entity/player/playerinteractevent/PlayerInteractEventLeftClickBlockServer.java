package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public abstract class PlayerInteractEventLeftClickBlockServer {

    @Shadow public abstract boolean isCreative();
    @Final
    @Shadow protected ServerPlayer player;

    @Inject(
            method = "handleBlockBreakAction",
            at = @At("HEAD"),
            cancellable = true)
    private void onClickBlock(BlockPos pos, ServerboundPlayerActionPacket.Action action, Direction face, int maxBuildHeight, int sequence, CallbackInfo ci) {
        var event = EventWrapperHooks.onLeftClickBlock(player, pos, face, action);
        if (event.isCanceled() || (!this.isCreative() && event.getResult() == EventWrapper.Result.DENY)) {
            ci.cancel();
        }
    }
}
