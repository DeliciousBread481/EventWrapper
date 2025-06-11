package io.github.lounode.eventwrapper.eventbus.api;

import io.github.lounode.eventwrapper.event.ServiceUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPlatformEventHelper {
	String forgePackageNamePrefix = "net.minecraftforge";
	IPlatformEventHelper INSTANCE = ServiceUtil.findService(IPlatformEventHelper.class, null);

	void register(Object target);

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

			if (!type.isPrimitive()) {
				continue;
			}

			try {
				Field toField = Arrays.stream(toFields).filter(field -> field.getName().equals(name)).findFirst().orElse(null);
				if (!Modifier.isFinal(toField.getModifiers()) && toField.getType().equals(type)) {
					fromField.setAccessible(true);
					toField.setAccessible(true);
					Object value = fromField.get(from);
					toField.set(to, value);
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
		List<Field> fieldList = new ArrayList<>(16);
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			fieldList.addAll(Arrays.stream(fields).filter(field -> !Modifier.isFinal(field.getModifiers())).toList());
			clazz = clazz.getSuperclass();
		}
		Field[] f = new Field[fieldList.size()];
		return fieldList.toArray(f);
	}

	<T extends EventWrapper> T post(T event);

	default boolean isCorrectToolForDrops(BlockState state, Player player) {
		return false;
	}
}
