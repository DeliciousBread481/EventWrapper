package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEventNameFormatEventPoster {

    @Inject(
            method = "getName",
            at = @At("RETURN"),
            cancellable = true)
    private void onGetDisplayName(CallbackInfoReturnable<Component> cir) {
        Component component = EventWrapperHooks.getPlayerDisplayName((Player)(Object)this, cir.getReturnValue());
        cir.setReturnValue(component == null ? cir.getReturnValue() : component);
    }
}
