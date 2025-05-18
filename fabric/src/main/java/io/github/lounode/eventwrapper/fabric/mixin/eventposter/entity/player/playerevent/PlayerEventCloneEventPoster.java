package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import net.minecraft.server.level.ServerPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerPlayer.class)
public class PlayerEventCloneEventPoster {

	@Inject(
		method = "restoreFrom",
		at = @At("RETURN")
	)
	private void onPlayerClone(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
		EventWrapperHooks.onPlayerClone((ServerPlayer) (Object) this, that, !keepEverything);
	}

}
