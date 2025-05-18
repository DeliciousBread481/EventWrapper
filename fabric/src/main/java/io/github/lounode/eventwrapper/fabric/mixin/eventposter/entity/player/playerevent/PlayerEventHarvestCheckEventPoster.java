package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(Player.class)
public abstract class PlayerEventHarvestCheckEventPoster {

	@Inject(
		method = "hasCorrectToolForDrops",
		at = @At("RETURN"),
		cancellable = true
	)
	private void onHarvestCheck(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			cir.setReturnValue(EventWrapperHooks.isCorrectToolForDrops(state, (Player) (Object) this));
		}
	}
}
