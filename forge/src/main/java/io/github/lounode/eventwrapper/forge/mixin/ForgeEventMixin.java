package io.github.lounode.eventwrapper.forge.mixin;

import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.forge.event.ForgeEventExtension;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Event.class)
@Implements(@Interface(iface = ForgeEventExtension.class, prefix = "EventWrapper$"))
public abstract class ForgeEventMixin {

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onNew(CallbackInfo ci) {
        System.out.println("NEWED EVENT");
    }

    @Unique
    @Nullable
    private EventWrapper EventWrapper_eventWrapper = null;

    public void EventWrapper_setEventWrapper(EventWrapper wrapper) {
        this.EventWrapper_eventWrapper = wrapper;
    }

    @Nullable
    public EventWrapper EventWrapper_getEventWrapper() {
        return EventWrapper_eventWrapper;
    }
}
