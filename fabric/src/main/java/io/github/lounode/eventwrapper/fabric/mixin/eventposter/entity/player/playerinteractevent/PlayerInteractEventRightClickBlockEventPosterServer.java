package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerPlayerGameMode.class)
public class PlayerInteractEventRightClickBlockEventPosterServer {

	@Inject(
		method = "useItemOn",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onUseItemOn(ServerPlayer player, Level level, ItemStack stack, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
		BlockPos blockpos = hitResult.getBlockPos();
		BlockState blockstate = level.getBlockState(blockpos);
		if (!blockstate.getBlock().isEnabled(level.enabledFeatures())) {
			cir.setReturnValue(InteractionResult.FAIL);
		}
		var event = EventWrapperHooks.onRightClickBlock(player, hand, blockpos, hitResult);
		if (event.isCanceled()) {
			cir.setReturnValue(event.getCancellationResult());
		}
	}
}
