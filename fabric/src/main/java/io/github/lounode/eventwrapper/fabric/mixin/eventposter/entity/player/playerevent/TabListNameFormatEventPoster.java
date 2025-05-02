package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class TabListNameFormatEventPoster {

    @Inject(
            method = "getTabListDisplayName",
            at = @At("RETURN"),
            cancellable = true)
    private void onGetTablistName(CallbackInfoReturnable<Component> cir) {
        PlayerEventWrapper.TabListNameFormat event = new PlayerEventWrapper.TabListNameFormat((ServerPlayer) (Object) this);

        EventsWrapper.post(event);

        if (event.getDisplayName() != null) {
            cir.setReturnValue(event.getDisplayName());
        }
    }
}
