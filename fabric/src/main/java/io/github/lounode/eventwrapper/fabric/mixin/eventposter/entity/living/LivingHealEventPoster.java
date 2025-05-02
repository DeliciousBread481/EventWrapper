package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingHealEventWrapper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingHealEventPoster {
    @ModifyVariable(method = "heal", at = @At("HEAD"), argsOnly = true)
    private float onLivingHeal(float healAmount) {
        LivingHealEventWrapper event = new LivingHealEventWrapper((LivingEntity) (Object) this, healAmount);
        EventsWrapper.post(event);

        if (event.isCanceled()) {
            return 0;
        }

        return event.getAmount();
    }
}
