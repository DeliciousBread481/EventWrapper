package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerEventPlayerRespawnEventPoster {

    @Inject(
            method = "respawn",
            at = @At("RETURN")
    )
    private void onRespawn(ServerPlayer player, boolean keepEverything, CallbackInfoReturnable<ServerPlayer> cir) {
        EventWrapperHooks.firePlayerRespawnEvent(player, keepEverything);
    }
}
