package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingTickEventPoster {

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onLivingTick(CallbackInfo ci) {
        LivingEventWrapper.LivingTickEvent event = new LivingEventWrapper.LivingTickEvent((LivingEntity) (Object) this);
        if (EventsWrapper.post(event).isCanceled()) {
            ci.cancel();
        }
    }
}
