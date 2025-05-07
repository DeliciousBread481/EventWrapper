package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerEntity.class)
public class PlayerEventStopTrackingEventPoster {

    @Shadow @Final private Entity entity;

    @Inject(
            method = "removePairing",
            at = @At("RETURN")
    )
    private void onStopTrack(ServerPlayer player, CallbackInfo ci) {
        EventWrapperHooks.onStopEntityTracking(this.entity, player);
    }
}
