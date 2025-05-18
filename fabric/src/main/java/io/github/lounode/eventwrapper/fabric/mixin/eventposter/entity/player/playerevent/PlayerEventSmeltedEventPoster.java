package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(FurnaceResultSlot.class)
public class PlayerEventSmeltedEventPoster {

	@Shadow
	@Final
	private Player player;

	@Inject(
		method = "checkTakeAchievements",
		at = @At("RETURN")
	)
	private void onCraft(ItemStack stack, CallbackInfo ci) {
		EventWrapperHooks.firePlayerSmeltedEvent(this.player, stack);
	}
}
