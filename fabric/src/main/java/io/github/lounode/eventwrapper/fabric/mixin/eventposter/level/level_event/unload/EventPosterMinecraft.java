package io.github.lounode.eventwrapper.fabric.mixin.eventposter.level.level_event.unload;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(Minecraft.class)
public class EventPosterMinecraft {

	@Shadow
	@Nullable
	public ClientLevel level;

	@Inject(
		method = "setLevel",
		at = @At("HEAD")
	)
	private void onSetLevel(ClientLevel levelClient, CallbackInfo ci) {
		if (level != null) {
			EventWrapperHooks.onLevelUnload(level);
		}
	}

	@Inject(
		method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateScreenAndTick(Lnet/minecraft/client/gui/screens/Screen;)V", shift = At.Shift.AFTER)
	)
	private void onClearLevel(Screen screen, CallbackInfo ci) {
		if (level != null) {
			EventWrapperHooks.onLevelUnload(level);
		}
	}
}
