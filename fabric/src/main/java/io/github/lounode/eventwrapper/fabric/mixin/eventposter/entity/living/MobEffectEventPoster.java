package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.living;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class MobEffectEventPoster {

    @Shadow @Final private Map<MobEffect, MobEffectInstance> activeEffects;

    @Shadow protected abstract void onEffectRemoved(MobEffectInstance effectInstance);

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "HEAD")
    )
    private void onEffectAdd(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self =  (LivingEntity)(Object)this;
        if (!self.canBeAffected(effectInstance)) {
            return;
        }
        MobEffectInstance mobEffectInstance = self.getActiveEffectsMap().get(effectInstance.getEffect());

        var event = new MobEffectEventWrapper.Added(
                self,
                mobEffectInstance,
                effectInstance,
                entity
        );

        EventsWrapper.post(event);
    }
    //Remove effect: 2 injects
    //1
    //I know this could curse many compatibility issues
    //But F-word mojang what S-word is this code?????
    //TODO inject get iter then return a special iter to post event and cancel remove
    @Inject(
            method = "removeAllEffects",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void redirectNext(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (self.level().isClientSide) {
            cir.setReturnValue(false);
        } else {
            Iterator<MobEffectInstance> iterator = this.activeEffects.values().iterator();

            boolean bl;
            for(bl = false; iterator.hasNext(); bl = true) {
                MobEffectInstance effectInstance = iterator.next();
                MobEffectEventWrapper.Remove event = new MobEffectEventWrapper.Remove(self, effectInstance);

                EventsWrapper.post(event);

                if (event.isCanceled()) { continue;}

                this.onEffectRemoved(effectInstance);
                iterator.remove();
            }

            cir.setReturnValue(bl);
        }
    }
    //2
    @Inject(
            method = "removeEffect",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRemoveEffect(MobEffect effect, CallbackInfoReturnable<Boolean> cir) {
        MobEffectEventWrapper.Remove event = new MobEffectEventWrapper.Remove((LivingEntity)(Object)this, effect);

        EventsWrapper.post(event);

        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }
    //Applicable
    @Inject(
            method = "canBeAffected",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCanBeAffected (MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        MobEffectEventWrapper.Applicable event = new MobEffectEventWrapper.Applicable(self, effectInstance);

        EventsWrapper.post(event);

        if (event.getResult() != EventWrapper.Result.DEFAULT) {
            cir.setReturnValue(event.getResult() == EventWrapper.Result.ALLOW);
        }
    }
}
