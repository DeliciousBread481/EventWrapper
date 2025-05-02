package io.github.lounode.eventwrapper.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.fabricmc.loader.impl.ModContainerImpl;


public class FabricCommonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer("eventwrapper").ifPresent(mod -> {
            if (mod instanceof ModContainerImpl impl) {
                AutoEventSubscriberRegistryFabric.register(impl);
            }
        });
    }

    public static boolean isDebugVersion() {
        String debugProperty = System.getProperty("debugEvent");
        return "true".equals(debugProperty);
    }
}
