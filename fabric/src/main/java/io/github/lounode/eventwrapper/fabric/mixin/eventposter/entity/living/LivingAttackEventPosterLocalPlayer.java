package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingAttackEventWrapper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LivingAttackEventPosterLocalPlayer {

    @Inject(
            method = "hurt",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingAttackEventWrapper event = new LivingAttackEventWrapper((LocalPlayer)(Object)this, source, amount);

        EventsWrapper.post(event);

        if (event.isCanceled()) cir.setReturnValue(false);
    }
}
