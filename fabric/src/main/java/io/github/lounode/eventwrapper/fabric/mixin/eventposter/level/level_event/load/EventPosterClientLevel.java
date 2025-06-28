package io.github.lounode.eventwrapper.fabric.mixin.eventposter.level.level_event.load;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ClientLevel.class)
public class EventPosterClientLevel {

	@Inject(
		method = "<init>",
		at = @At("RETURN")
	)
	private void onLevelLoad(ClientPacketListener connection, ClientLevel.ClientLevelData clientLevelData, ResourceKey<Level> dimension, Holder<DimensionType> dimensionType, int viewDistance, int serverSimulationDistance, Supplier<ProfilerFiller> profiler, LevelRenderer levelRenderer, boolean isDebug, long biomeZoomSeed, CallbackInfo ci) {
		EventWrapperHooks.onLevelLoad((ClientLevel) (Object) this);
	}
}
