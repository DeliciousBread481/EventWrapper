package io.github.lounode.eventwrapper.fabric.mixin.eventposter.level.level_event.unload;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(MinecraftServer.class)
public class EventPosterMinecraftServer {

	@Inject(
		method = "stopServer",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;close()V")
	)
	private void onLevelUnload(CallbackInfo ci, @Local ServerLevel serverLevel) {
		EventWrapperHooks.onLevelUnload(serverLevel);
	}
}
