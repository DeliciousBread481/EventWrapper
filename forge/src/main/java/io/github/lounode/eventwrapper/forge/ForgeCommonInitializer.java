package io.github.lounode.eventwrapper.forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static io.github.lounode.eventwrapper.forge.ForgeCommonInitializer.MOD_ID;

@Mod(MOD_ID)
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

    public static boolean isDebugVersion() {
        String debugProperty = System.getProperty("debugEvent");
        return "true".equals(debugProperty);
    }

}
