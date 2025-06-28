package io.github.lounode.eventwrapper.fabric.mixin.eventposter.level.level_event.create_spawn_position;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(MinecraftServer.class)
public class EventPosterMinecraftServer {

	@Inject(
		method = "setInitialSpawn",
		at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/level/ServerLevel;getChunkSource()Lnet/minecraft/server/level/ServerChunkCache;"),
		cancellable = true
	)
	private static void onSetInitialSpawn(ServerLevel level, ServerLevelData levelData, boolean generateBonusChest, boolean debug, CallbackInfo ci) {
		boolean cancel = EventWrapperHooks.onCreateWorldSpawn(level, levelData);
		if (cancel) {
			ci.cancel();
		}
	}
}
