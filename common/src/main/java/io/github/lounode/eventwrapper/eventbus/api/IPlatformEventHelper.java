package io.github.lounode.eventwrapper.eventbus.api;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface IPlatformEventHelper {

	Map<Class<?>, Field[]> NON_FINAL_FIELD_CACHE = new Reference2ReferenceOpenHashMap<>();

	String forgePackageNamePrefix = "net.minecraftforge";
	IPlatformEventHelper INSTANCE = ServiceUtil.findService(IPlatformEventHelper.class, null);

	void register(Object target);
	void unregister(Object object);

	default boolean isForge() {
		return false;
	}

	default boolean isFabric() {
		return false;
	}

	//TODO 自动推导forge类型 类生成

	static void syncEventData(Object from, Object to) {
		if (from == null || to == null) {
			return;
		}

		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();

		Field[] fromFields = getFieldsWithoutFinal(fromClass);
		Field[] toFields = getFieldsWithoutFinal(toClass);

		for (Field fromField : fromFields) {
			String name = fromField.getName();
			Class<?> type = fromField.getType();

			try {
				for (Field toField : toFields) {
					if (toField.getType() == type && toField.getName().equals(name)) {
						Object value = fromField.get(from);
						toField.set(to, value);
					}
				}
			} catch (IllegalAccessException ignored) {}
		}
	}

	static Field[] getFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>(16);
		while (clazz != null) {
			fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fieldList.toArray(new Field[0]);
	}

	static Field[] getFieldsWithoutFinal(Class<?> clazz) {
		return NON_FINAL_FIELD_CACHE.computeIfAbsent(clazz, c -> {
			List<Field> fieldList = new ArrayList<>(16);
			while (c != null) {
				for (Field field : c.getDeclaredFields()) {
					if (!Modifier.isFinal(field.getModifiers())) {
						field.setAccessible(true);
						fieldList.add(field);
					}
				}
				c = c.getSuperclass();
			}
			return fieldList.toArray(new Field[0]);
		});
	}

	<T extends EventWrapper> T post(T event);

	default boolean isCorrectToolForDrops(BlockState state, Player player) {
		return false;
	}
}
