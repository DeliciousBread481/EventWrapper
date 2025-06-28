package io.github.lounode.eventwrapper.fabric.mixin.eventposter.level.level_event.load;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(MinecraftServer.class)
public class EventPosterMinecraftServer {

	@Shadow
	@Final
	private Map<ResourceKey<Level>, ServerLevel> levels;

	@Inject(
		method = "createLevels",
		at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/level/ServerLevel;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;", ordinal = 0)
	)
	private void onOverWorldCreate(ChunkProgressListener listener, CallbackInfo ci) {
		EventWrapperHooks.onLevelLoad(levels.get(Level.OVERWORLD));
	}

	@Inject(
		method = "createLevels",
		at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 1)
	)
	private void onOtherWorldCreate(ChunkProgressListener listener, CallbackInfo ci, @Local(ordinal = 1) ResourceKey<Level> resourceKey) {
		EventWrapperHooks.onLevelLoad(levels.get(resourceKey));
	}
}
