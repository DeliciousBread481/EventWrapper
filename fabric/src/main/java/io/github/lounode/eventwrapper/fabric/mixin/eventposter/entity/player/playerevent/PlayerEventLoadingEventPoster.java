package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(PlayerDataStorage.class)
public class PlayerEventLoadingEventPoster {

    @Shadow @Final
    public File playerDir;

    @Inject(
            method = "load",
            at = @At("RETURN")
    )
    private void onLoading(Player player, CallbackInfoReturnable<CompoundTag> cir) {
        EventWrapperHooks.firePlayerLoadingEvent(player, this.playerDir, player.getStringUUID());
    }
}
