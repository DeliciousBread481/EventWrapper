package io.github.lounode.eventwrapper.fabric;

import io.github.lounode.eventwrapper.event.entity.living.LivingAttackEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingDamageEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownStartEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.effect.MobEffectCategory;

@EventBusSubscriberWrapper
public class FabricCommonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {

    }
    @SubscribeEventWrapper
    public static void onDamage(LivingAttackEventWrapper event) {
        if (isDebugVersion()) {
            event.setCanceled(true);
        }
    }
    /*
    @SubscribeEventWrapper
    public static void onEffect(MobEffectEventWrapper.Applicable event) {
        if (isDebugVersion()) {
            //LOGGER.info("Effect added: " + event.getEntity());
            if (event.getEffectInstance().getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                event.setResult(EventWrapper.Result.DENY);
            }
        }
    }
    @SubscribeEventWrapper
    public static void onEffectRemove(MobEffectEventWrapper.Remove event) {
        if (isDebugVersion()) {
            event.setCanceled(true);
        }
    }
    @SubscribeEventWrapper
    public static void onEffect(MobEffectEventWrapper.Added event) {
        if (isDebugVersion()) {
            System.out.println("Effect added: " + event.getEntity());
        }
    }

    @SubscribeEventWrapper
    public static void onCooldownStart(ItemCooldownStartEventWrapper event) {
        if (isDebugVersion()) {
            event.setCanceled(true);
        }
    }
    */
    public static boolean isDebugVersion() {
        String debugProperty = System.getProperty("debugEvent");
        return "true".equals(debugProperty);
    }
}
