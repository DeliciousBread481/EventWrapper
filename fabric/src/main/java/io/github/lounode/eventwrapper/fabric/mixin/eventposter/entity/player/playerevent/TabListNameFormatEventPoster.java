package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
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
        Component component = EventWrapperHooks.getPlayerTabListDisplayName((ServerPlayer) (Object) this);
        if (component != null) {
            cir.setReturnValue(component);
        }
    }
}
