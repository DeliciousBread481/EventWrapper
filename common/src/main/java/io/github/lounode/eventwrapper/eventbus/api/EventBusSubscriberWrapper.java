package io.github.lounode.eventwrapper.eventbus.api;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EventBusSubscriberWrapper {
    //Dist[] value() default {Dist.CLIENT, Dist.DEDICATED_SERVER};

    //String modid() default "";

    /*

    Mod.EventBusSubscriber.Bus bus() default Mod.EventBusSubscriber.Bus.FORGE;

    public static enum Bus {
        FORGE(Bindings.getForgeBus()),
        MOD(() -> FMLJavaModLoadingContext.get().getModEventBus());

        private final Supplier<IEventBus> busSupplier;

        private Bus(Supplier<IEventBus> eventBusSupplier) {
            this.busSupplier = eventBusSupplier;
        }

        public Supplier<IEventBus> bus() {
            return this.busSupplier;
        }
    }

     */
}
