package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerPlayer.class)
public abstract class PlayerEventChangedDimensionEventPoster {

	@Shadow
	@Nullable
	protected abstract PortalInfo findDimensionEntryPoint(ServerLevel destination);

	@Shadow
	public abstract ServerLevel serverLevel();

	@Inject(
		method = "changeDimension",
		at = @At(value = "RETURN")
	)
	private void onChangedDim(ServerLevel destination, CallbackInfoReturnable<Entity> cir) {
		ServerLevel serverLevel = this.serverLevel();
		ResourceKey<Level> resourceKey = serverLevel.dimension();
		if (resourceKey == Level.END && destination.dimension() == Level.OVERWORLD) {

		} else {
			PortalInfo portalInfo = this.findDimensionEntryPoint(destination);
			if (portalInfo != null) {
				EventWrapperHooks.firePlayerChangedDimensionEvent((ServerPlayer) (Object) this, resourceKey, destination.dimension());
			}
		}
	}
}
