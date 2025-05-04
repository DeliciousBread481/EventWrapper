package io.github.lounode.eventwrapper.eventbus.api;


import io.github.lounode.eventwrapper.event.LockHelper;

import java.lang.annotation.Annotation;
import java.util.IdentityHashMap;

import static io.github.lounode.eventwrapper.eventbus.api.EventWrapper.HasResult;
public class EventListenerHelper {

    private static final LockHelper<Class<?>, Boolean> cancelable = new LockHelper<>(new IdentityHashMap<>());
    private static final LockHelper<Class<?>, Boolean> hasResult = new LockHelper<>(new IdentityHashMap<>());

    static boolean isCancelable(Class<?> eventClass) {
        return hasAnnotation(eventClass, Cancelable.class, cancelable);
    }

    static boolean hasResult(Class<?> eventClass) {
        return hasAnnotation(eventClass, HasResult.class, hasResult);
    }

    private static boolean hasAnnotation(Class<?> eventClass, Class<? extends Annotation> annotation, LockHelper<Class<?>, Boolean> lock) {
        if (eventClass == EventWrapper.class)
            return false;

        return lock.computeIfAbsent(eventClass, () -> {
            var parent = eventClass.getSuperclass();
            return eventClass.isAnnotationPresent(annotation) || (parent != null && hasAnnotation(parent, annotation, lock));
        });
    }
}
