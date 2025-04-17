package io.github.lounode.eventwrapper.forge;

import com.mojang.logging.LogUtils;
import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownStartEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static io.github.lounode.eventwrapper.forge.ForgeCommonInitializer.MOD_ID;

@Mod(MOD_ID)
@EventBusSubscriberWrapper
public class ForgeCommonInitializer {
    public static final String MOD_ID = "eventwrapper";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ForgeCommonInitializer(FMLJavaModLoadingContext context)
    {
        var scanData = ModList.get().getAllScanData();

        for (var data : scanData) {
            AutoEventSubscriberRegistry.inject(data);
        }
    }


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
    public static void onCooldownStart(ItemCooldownStartEventWrapper event) {
        if (isDebugVersion()) {
            event.setCanceled(true);
        }
    }

    public static boolean isDebugVersion() {
        String debugProperty = System.getProperty("debugEvent");
        return "true".equals(debugProperty);
    }

}
