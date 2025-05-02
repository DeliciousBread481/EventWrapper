package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
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
        PlayerEventWrapper.NameFormat event = new PlayerEventWrapper.NameFormat((Player)(Object)this, cir.getReturnValue());
        EventsWrapper.post(event);
        cir.setReturnValue(event.getDisplayname() == null ? event.getUsername() : event.getDisplayname());
    }
}
