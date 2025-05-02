package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingVisibilityEventPoster {

    @Inject(
            method = "getVisibilityPercent",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onLivingGetVisibilityPercent(Entity lookingEntity, CallbackInfoReturnable<Double> cir) {
        LivingEventWrapper.LivingVisibilityEvent event = new LivingEventWrapper.LivingVisibilityEvent((LivingEntity) (Object) this, lookingEntity, cir.getReturnValue());
        EventsWrapper.post(event);
        cir.setReturnValue(Math.max(0.0D, event.getVisibilityModifier()));
    }
}
