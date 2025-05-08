package io.github.lounode.eventwrapper.fabric.mixin.eventposter.furnace;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceMenu.class)
public class FurnaceBurnTimeEventPosterFurnaceMenu {

    @Shadow @Final private RecipeType<? extends AbstractCookingRecipe> recipeType;

    @Inject(
            method = "isFuel",
            at = @At("RETURN"),
            cancellable = true
    )
    private void isFuel(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(EventWrapperHooks.getBurnTime(stack, this.recipeType) > 0);
    }
}
