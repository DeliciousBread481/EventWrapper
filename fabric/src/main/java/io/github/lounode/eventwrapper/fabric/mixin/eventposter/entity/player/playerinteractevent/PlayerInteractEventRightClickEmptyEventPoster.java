package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(Minecraft.class)
public class PlayerInteractEventRightClickEmptyEventPoster {

	@Shadow
	@Nullable
	public HitResult hitResult;

	@Shadow
	@Nullable
	public LocalPlayer player;

	@Inject(
		method = "startUseItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
			ordinal = 1
		)
	)
	private void onInteract(CallbackInfo ci, @Local ItemStack itemstack, @Local InteractionHand interactionHand) {
		if (itemstack.isEmpty() && (this.hitResult == null || this.hitResult.getType() == HitResult.Type.MISS)) {
			EventWrapperHooks.onEmptyClick(this.player, interactionHand);
		}
	}
}
