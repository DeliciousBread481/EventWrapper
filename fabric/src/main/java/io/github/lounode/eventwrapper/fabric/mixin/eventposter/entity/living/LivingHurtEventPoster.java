package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingHurtEventWrapper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingHurtEventPoster {

    @ModifyVariable(
            method = "actuallyHurt",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private float modifyIncomingDamage(float originalDamage, DamageSource damageSource) {
        LivingHurtEventWrapper event = new LivingHurtEventWrapper((LivingEntity) (Object) this, damageSource, originalDamage);

        EventsWrapper.post(event);

        if (event.isCanceled()) return 0;

        return event.getAmount();
    }
}
