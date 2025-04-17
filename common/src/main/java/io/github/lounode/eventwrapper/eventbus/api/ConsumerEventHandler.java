package io.github.lounode.eventwrapper.eventbus.api;

import io.github.lounode.eventwrapper.eventbus.IWrapperListener;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Wraps a consumer to be used as an event handler, and overrides {@link #toString()} for better debugging.
 */
public sealed class ConsumerEventHandler extends EventListener {
    protected final Consumer<EventWrapper> consumer;

    public ConsumerEventHandler(Consumer<EventWrapper> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(EventWrapper event) {
        consumer.accept(event);
    }

    @Override
    public String toString() {
        return consumer.toString();
    }

    public static final class WithPredicate extends ConsumerEventHandler implements IWrapperListener {
        private final Predicate<EventWrapper> predicate;
        private final EventListener withoutCheck;

        public WithPredicate(Consumer<EventWrapper> consumer, Predicate<EventWrapper> predicate) {
            super(consumer);
            this.predicate = predicate;
            this.withoutCheck = new ConsumerEventHandler(consumer);
        }

        @Override
        public void invoke(EventWrapper event) {
            if (predicate.test(event)) {
                consumer.accept(event);
            }
        }

        @Override
        public EventListener getWithoutCheck() {
            return withoutCheck;
        }
    }
}
