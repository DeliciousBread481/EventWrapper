package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerEventLogoutEventPoster {

    @Inject(
            method = "remove",
            at = @At("HEAD")
    )
    private void onPlayerLogout(ServerPlayer player, CallbackInfo ci) {
        EventsWrapper.post(new PlayerEventWrapper.PlayerLoggedOutEvent(player));
    }
}
