package io.github.lounode.eventwrapper.fabric.mixin.eventposter.furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceBurnTimeEventPosterFurnaceBlockEntity {
	@Unique
	private RecipeType<? extends AbstractCookingRecipe> recipeType;

	@Inject(
		method = "<init>",
		at = @At("RETURN")
	)
	private void onInit(BlockEntityType<?> type, BlockPos pos, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType, CallbackInfo ci) {
		this.recipeType = recipeType;
	}

	@Inject(
		method = "getBurnDuration",
		at = @At("RETURN"),
		cancellable = true
	)
	private void getFuelDuration(ItemStack fuel, CallbackInfoReturnable<Integer> cir) {
		if (fuel.isEmpty()) {
			return;
		}
		cir.setReturnValue(EventWrapperHooks.getBurnTime(fuel, this.recipeType));
	}

	@Inject(
		method = "isFuel",
		at = @At("RETURN"),
		cancellable = true
	)
	private static void isFuel(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(EventWrapperHooks.getBurnTime(stack, null) > 0);
	}

	@Inject(
		method = "canPlaceItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;isFuel(Lnet/minecraft/world/item/ItemStack;)Z"
		),
		cancellable = true
	)
	private void canPlaceItem(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(EventWrapperHooks.getBurnTime(stack, null) > 0);
	}
}
