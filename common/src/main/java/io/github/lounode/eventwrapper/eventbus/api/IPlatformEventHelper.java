package io.github.lounode.eventwrapper.eventbus.api;

import net.minecraftforge.eventbus.api.Event;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.lounode.eventwrapper.event.ServiceUtil;

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
}
