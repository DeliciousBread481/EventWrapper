package io.github.lounode.eventwrapper.forge;

import net.minecraftforge.fml.Logging;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.stream.Collectors;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.eventbus.api.Dist;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.OnlyIn;

public class AutoEventSubscriberRegistry {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Type AUTO_SUBSCRIBER = Type.getType(EventBusSubscriberWrapper.class);
	private static final Type MOD_TYPE = Type.getType(Mod.class);
	private static final Type ONLY_IN_TYPE = Type.getType(OnlyIn.class);
	private static final Type FORGE_ONLY_IN_TYPE = Type.getType(net.minecraftforge.api.distmarker.OnlyIn.class);

	public static void inject(String modId) {
		var scanDatas = ModList.get().getAllScanData();

		ModFileScanData result = null;

		for (var scanData : scanDatas) {
			var modids = scanData.getAnnotations().stream()
					.filter(data -> MOD_TYPE.equals(data.annotationType()))
					.collect(Collectors.toMap(a -> a.clazz().getClassName(), a -> (String) a.annotationData().get("value")));
			if (modids.values().contains(modId)) {
				result = scanData;
			}
		}

		if (result == null) {
			LOGGER.error("Can't find mod scan data: id: " + modId);
		}

		inject(result);
	}

	public static void inject(ModFileScanData scanData) {
		if (scanData == null) {
			return;
		}
		//LOGGER.debug(Logging.LOADING, "Attempting to inject @EventBusSubscriber classes into the eventbus for {}", mod.getModId());

		var targets = scanData.getAnnotations().stream()
				.filter(data -> AUTO_SUBSCRIBER.equals(data.annotationType()))
				.toList();

		var clientOnlyClasses = scanData.getAnnotations().stream()
				.filter(data -> ONLY_IN_TYPE.equals(data.annotationType()) || FORGE_ONLY_IN_TYPE.equals(data.annotationType()))
				.filter(data -> {
					try {
						var value = data.annotationData().get("value");
						return value == Dist.CLIENT || value == net.minecraftforge.api.distmarker.Dist.CLIENT;
					} catch (Exception e) {
						return false;
					}
				})
				.map(data -> data.clazz().getClassName())
				.collect(Collectors.toSet());

		for (var data : targets) {

			if (FMLEnvironment.dist != net.minecraftforge.api.distmarker.Dist.CLIENT && clientOnlyClasses.contains(data.clazz().getClassName())) {
				LOGGER.warn("Skipping client class {}", data.clazz().getClassName());
				continue;
			}
			/*
			@SuppressWarnings("unchecked")
			final List<ModAnnotation.EnumHolder> sidesValue = (List<ModAnnotation.EnumHolder>)data.annotationData().
					getOrDefault("value", Arrays.asList(new ModAnnotation.EnumHolder(null, "CLIENT"), new ModAnnotation.EnumHolder(null, "DEDICATED_SERVER")));
			
			final EnumSet<Dist> sides = sidesValue.stream().map(eh -> Dist.valueOf(eh.getValue())).
					collect(Collectors.toCollection(() -> EnumSet.noneOf(Dist.class)));
			
			
			if (FMLEnvironment.dist) {
			*/
			LOGGER.debug(Logging.LOADING, "Auto-subscribing {}", data.clazz().getClassName());
			try {
				EventsWrapper.register(Class.forName(data.clazz().getClassName()));
			} catch (RuntimeException e) {
				LOGGER.error(Logging.LOADING, "Error when register event in {}.", data.clazz().getClassName());
			} catch (ClassNotFoundException e) {
				LOGGER.error(Logging.LOADING, "Failed to load mod class {} for @EventBusSubscriber annotation", data.clazz(), e);
			} catch (NoClassDefFoundError e) {
				LOGGER.error(Logging.LOADING, "Class {} was not found", data.clazz(), e);
			}
			//}
		}
	}

	@SuppressWarnings("unchecked")
	private static <R> R value(ModFileScanData.AnnotationData data, String key, R value) {
		return (R) data.annotationData().getOrDefault(key, value);
	}
}
