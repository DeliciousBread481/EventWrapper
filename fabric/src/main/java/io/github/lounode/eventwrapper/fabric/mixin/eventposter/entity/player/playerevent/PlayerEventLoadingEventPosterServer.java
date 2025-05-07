package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerEventLoadingEventPosterServer {

    @Shadow @Final private PlayerDataStorage playerIo;

    @Inject(
            method = "load",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;load(Lnet/minecraft/nbt/CompoundTag;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void onPlayerLoad(ServerPlayer player, CallbackInfoReturnable<CompoundTag> cir) {
        EventWrapperHooks.firePlayerLoadingEvent(player, this.playerIo, player.getUUID().toString());
    }
}
