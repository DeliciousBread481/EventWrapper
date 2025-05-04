package io.github.lounode.eventwrapper.fabric;

import io.github.lounode.eventwrapper.EventsWrapper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.ModContainerImpl;

public class PreLaunchHandler implements PreLaunchEntrypoint {
    public static final boolean ENABLED = System.getProperty("fabric-api.gametest") != null;

    @Override
    public void onPreLaunch() {
        EventsWrapper.WRAPPER_EVENT_BUS.start();

        if (ENABLED) {
            FabricLoader.getInstance().getModContainer("eventwrapper").ifPresent(mod -> {
                if (mod instanceof ModContainerImpl impl) {
                    AutoRegisterGameTest.register(impl);
                }
            });
        }
    }
}
