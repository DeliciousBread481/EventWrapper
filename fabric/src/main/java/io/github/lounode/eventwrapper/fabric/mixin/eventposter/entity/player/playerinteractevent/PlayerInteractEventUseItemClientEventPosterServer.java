package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerPlayerGameMode.class)
public class PlayerInteractEventUseItemClientEventPosterServer {

	@Shadow
	private GameType gameModeForPlayer;

	@Inject(
		method = "useItem",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onUseItem(ServerPlayer player, Level level, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		if (this.gameModeForPlayer == GameType.SPECTATOR) {
			cir.setReturnValue(InteractionResult.PASS);
		} else if (player.getCooldowns().isOnCooldown(stack.getItem())) {
			cir.setReturnValue(InteractionResult.PASS);
		} else {
			InteractionResult cancelResult = EventWrapperHooks.onItemRightClick(player, hand);
			if (cancelResult != null) {
				cir.setReturnValue(cancelResult);
			}
		}
	}
}
