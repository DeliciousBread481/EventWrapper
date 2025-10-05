package io.github.lounode.eventwrapper.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.loading.FMLEnvironment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Stream;

import io.github.lounode.eventwrapper.eventbus.api.EventConverter;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.IPlatformEventHelper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.forge.event.ForgeEventMappings;

public class ForgeEventHelper implements IPlatformEventHelper {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final Map<Event, EventWrapper> FORGE_EVENT_TRACKER_MAP = new WeakHashMap<>();

	@Override
	public boolean isForge() {
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends EventWrapper> T post(T event) {
		EventConverter converter = ForgeEventMappings.getConverter(event.getClass());

		if (converter == null) {
			LOGGER.error("Can't find the converter: {}", event.getClass());
			return event;
		}

		Event forgeEvent = (Event) converter.toEvent(event);

		/*
		
		if (!(forgeEvent instanceof ForgeEventExtension extension)) {
			LOGGER.error("Event extension does not existed: {}", forgeEvent.getClass());
			return event;
		}
		
		extension.EventWrapper_setEventWrapper(event);
		
		*/
		track(forgeEvent, event);

		MinecraftForge.EVENT_BUS.post(forgeEvent);

		/*
		Event forgeEvent = (Event) event.toForgeEvent();
		track(forgeEvent, event);
		
		//Because Wrapper -> ForgeEvent -> ForgeBus
		//The reference will lose during translation
		//So we use a WeakHashMap to keep additional reference
		//Anti-GC
		Object[] holder = new Object[] { event };
		MinecraftForge.EVENT_BUS.post(forgeEvent);
		//Anti-GC
		holder[0] = null;
		
		*/

		return event;
	}

	static Class<? extends Event> getForgeEventClass(Class<? extends EventWrapper> wrapperClass) {
		String wrapperFullName = wrapperClass.getName();
		if (wrapperFullName.contains("Wrapper")) {
			try {
				String forgeClassName = wrapperFullName.replace("Wrapper", "");

				forgeClassName = forgeClassName.replace("io.github.lounode.eventwrapper", forgePackageNamePrefix);

				@SuppressWarnings("unchecked")
				Class<? extends Event> forgeClass = (Class<? extends Event>) Class.forName(forgeClassName);
				return forgeClass;
			} catch (ClassNotFoundException ignored) {}
		}

		try {
			Method getForgeClassMethod = wrapperClass.getMethod("getForgeClass");
			@SuppressWarnings("unchecked")
			Class<? extends Event> forgeClass = (Class<? extends Event>) getForgeClassMethod.invoke(null);
			return forgeClass;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get forge class from wrapper: " + wrapperClass.getName(), e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void registerListener(Object target, Method method) {
		Class<?>[] params = method.getParameterTypes();
		if (params.length != 1) {
			throw new IllegalArgumentException("Event subscriber can only subscribe one event!");
		}
		if (!EventWrapper.class.isAssignableFrom(params[0])) {
			throw new IllegalArgumentException("EventType must extend EventWrapper");
		}

		@SuppressWarnings("unchecked")
		Class<? extends EventWrapper> wrapperClass = (Class<? extends EventWrapper>) params[0];

		EventConverter converter = ForgeEventMappings.getConverter(wrapperClass);

		if (converter == null) {
			LOGGER.error("Can't find the converter: {}", wrapperClass);
			return;
		}

		Class<? extends Event> forgeEventClass = ForgeEventMappings.getForgeEventClass(wrapperClass);

		if (forgeEventClass == null) {
			LOGGER.error("Wrapper was not found forge event class: {}", wrapperClass);
			return;
		}

		SubscribeEventWrapper annotation = method.getAnnotation(SubscribeEventWrapper.class);
		EventPriority priority = annotation != null ? EventPriority.valueOf(annotation.priority().name()) : EventPriority.NORMAL;
		boolean receiveCanceled = annotation != null && annotation.receiveCanceled();

		method.setAccessible(true);
		MinecraftForge.EVENT_BUS.addListener(priority, receiveCanceled, forgeEventClass, event -> {
			try {
				EventWrapper wrapper;
				/*
				if (!(event instanceof ForgeEventExtension extension)) {
					LOGGER.error("Event extension does not existed: {}", event.getClass());
					return;
				}
				
				*/

				if ((wrapper = FORGE_EVENT_TRACKER_MAP.get(event)) == null) {
					wrapper = converter.toWrapper(event);
				} else {
					IPlatformEventHelper.syncEventData(event, wrapper);
				}

				/*
				
				if (extension.EventWrapper_getEventWrapper() != null) {
					extension.EventWrapper_setEventWrapper(wrapper);
				}
				
				*/

				method.invoke(target, wrapper);

				IPlatformEventHelper.syncEventData(wrapper, event);
				/*
				
				Constructor<?> ctor = wrapperClass.getConstructor(forgeEventClass);
				
				EventWrapper wrapperEvent;
				wrapperEvent = getWrapper(event);
				
				if (wrapperEvent == null) {
					wrapperEvent = (EventWrapper) ctor.newInstance(event);
				}
				
				EventConverter converter = ForgeEventMappings.getConverter(wrapperEvent.getClass());
				if (converter != null) {
					wrapperEvent = converter.toWrapper(event);
				}
				
				method.setAccessible(true);
				method.invoke(target, wrapperEvent);
				IPlatformEventHelper.syncEventData(wrapperEvent, event);
				
				*/
			} catch (Exception e) {
				throw new RuntimeException("Event call Error!", e);
			}
		});
	}

	@Override
	public void register(Object target) {
		boolean isStatic = target.getClass() == Class.class;
		Class<?> clazz = isStatic ? (Class<?>) target : target.getClass();

		checkSupertypes(clazz, clazz);
		int foundMethods = 0;

		if (clazz.isAnnotationPresent(OnlyIn.class)) {
			if (clazz.getAnnotation(OnlyIn.class).value() == Dist.CLIENT && FMLEnvironment.dist != Dist.CLIENT) {
				return;
			}
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(SubscribeEventWrapper.class)) {
				continue;
			}
			if (method.isAnnotationPresent(OnlyIn.class)) {
				if (method.getAnnotation(OnlyIn.class).value() == Dist.CLIENT && FMLEnvironment.dist != Dist.CLIENT) {
					return;
				}
			}

			if (Modifier.isStatic(method.getModifiers()) == isStatic) {
				registerListener(target, method);
			} else {
				if (isStatic) {
					throw new IllegalArgumentException("""
							Expected @SubscribeEvent method %s to be static
							because register() was called with a class type.
							Either make the method static, or call register() with an instance of %s.
							""".formatted(method, clazz));
				} else {
					throw new IllegalArgumentException("""
							Expected @SubscribeEvent method %s to NOT be static
							because register() was called with an instance type.
							Either make the method non-static, or call register(%s.class).
							""".formatted(method, clazz.getSimpleName()));
				}
			}

			++foundMethods;
		}

		if (foundMethods == 0) {
			throw new IllegalArgumentException("""
					%s has no @SubscribeEvent methods, but register was called anyway.
					The event bus only recognizes listener methods that have the @SubscribeEvent annotation.
					""".formatted(clazz)
			);
		}
	}

	@Override
	public void unregister(Object object) {
		MinecraftForge.EVENT_BUS.unregister(object);
	}

	private static void checkSupertypes(Class<?> registeredType, Class<?> type) {
		if (type == null || type == Object.class) {
			return;
		}

		if (type != registeredType) {
			for (var method : type.getDeclaredMethods()) {
				if (method.isAnnotationPresent(SubscribeEventWrapper.class)) {
					throw new IllegalArgumentException("""
							Attempting to register a listener object of type %s,
							however its supertype %s has a @SubscribeEvent method: %s.
							This is not allowed! Only the listener object can have @SubscribeEvent methods.
							""".formatted(registeredType, type, method));
				}
			}
		}

		checkSupertypes(registeredType, type.getSuperclass());
		Stream.of(type.getInterfaces())
				.forEach(itf -> checkSupertypes(registeredType, itf));
	}

	public static void track(Event forgeEvent, EventWrapper wrapper) {
		FORGE_EVENT_TRACKER_MAP.put(forgeEvent, wrapper);
	}

	public static EventWrapper getWrapper(Event forgeEvent) {
		return FORGE_EVENT_TRACKER_MAP.get(forgeEvent);
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState state, Player player) {
		return ForgeHooks.isCorrectToolForDrops(state, player);
	}
}
