package io.github.lounode.eventwrapper.forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import static io.github.lounode.eventwrapper.forge.ForgeCommonInitializer.MOD_ID;

@Mod(MOD_ID)
public class ForgeCommonInitializer {
    public static final String MOD_ID = "eventwrapper";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Type MOD_TYPE = Type.getType(Mod.class);

    public ForgeCommonInitializer()
    {
        var modInfos = ModList.get().getMods();

        for (var modInfo : modInfos) {
            var dependOnSelf = modInfo.getDependencies().stream()
                    .anyMatch(dep -> dep.getModId().equals(MOD_ID));
            var isSelf = modInfo.getModId().equals(MOD_ID);

            if (dependOnSelf || isSelf) {
                AutoEventSubscriberRegistry.inject(modInfo.getModId());
            }
        }
    }

    public static boolean isDebugVersion() {
        String debugProperty = System.getProperty("debugEvent");
        return "true".equals(debugProperty);
    }

}
