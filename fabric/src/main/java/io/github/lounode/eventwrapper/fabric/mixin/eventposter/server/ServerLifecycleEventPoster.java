package io.github.lounode.eventwrapper.fabric.mixin.eventposter.server;

import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(MinecraftServer.class)
public class ServerLifecycleEventPoster {

	@Inject(
		method = "runServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/MinecraftServer;initServer()Z"
		)
	)
	private void onServerAboutToStart(CallbackInfo ci) {
		EventWrapperHooks.onServerAboutToStart((MinecraftServer) (Object) this);
	}

	@Inject(
		method = "runServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/MinecraftServer;initServer()Z",
			shift = At.Shift.AFTER
		)
	)
	private void onServerStarting(CallbackInfo ci) {
		EventWrapperHooks.onServerStarting((MinecraftServer) (Object) this);
	}

	@Inject(
		method = "runServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/MinecraftServer;buildServerStatus()Lnet/minecraft/network/protocol/status/ServerStatus;"
		)
	)
	private void onServerStarted(CallbackInfo ci) {
		EventWrapperHooks.onServerStarted((MinecraftServer) (Object) this);
	}

	@Inject(
		method = "runServer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/MinecraftServer;stopServer()V"
		)
	)
	private void onServerStopping(CallbackInfo ci) {
		EventWrapperHooks.onServerStopping((MinecraftServer) (Object) this);
	}

	@Inject(
		method = "onServerExit",
		at = @At("HEAD")
	)
	private void onServerStopped(CallbackInfo ci) {
		EventWrapperHooks.onServerStopped((MinecraftServer) (Object) this);
	}

}
