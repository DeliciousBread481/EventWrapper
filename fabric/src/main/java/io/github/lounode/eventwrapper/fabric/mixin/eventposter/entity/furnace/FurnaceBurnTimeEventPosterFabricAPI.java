package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.furnace;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FuelRegistryImpl.class)
public class FurnaceBurnTimeEventPosterFabricAPI {

    @Inject(
            method = "get(Lnet/minecraft/world/level/ItemLike;)Ljava/lang/Integer;",
            at = @At("RETURN"),
            cancellable = true)
    private void getBurnTime(ItemLike item, CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = new ItemStack(item);
        cir.setReturnValue(EventWrapperHooks.getBurnTime(stack, null));
    }
}
