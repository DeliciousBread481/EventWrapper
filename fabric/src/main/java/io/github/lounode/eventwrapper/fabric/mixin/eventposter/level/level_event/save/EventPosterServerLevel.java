package io.github.lounode.eventwrapper.fabric.mixin.eventposter.level.level_event.save;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerLevel.class)
public class EventPosterServerLevel {

	@Inject(
		method = "save",
		at = @At("RETURN")
	)
	private void onLevelSave(ProgressListener progress, boolean flush, boolean skipSave, CallbackInfo ci) {
		if (!skipSave) {
			EventWrapperHooks.onLevelSave((ServerLevel) (Object) this);
		}
	}
}
