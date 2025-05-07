package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class PlayerEventItemPickupEventPoster {

    @Inject(
            method = "playerTouch",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"),
            cancellable = true)
    private void beforePickup(Player player, CallbackInfo ci, @Local ItemStack itemStack, @Share("count") LocalIntRef i, @Share("hook") LocalIntRef hook) {
        i.set(itemStack.getCount());
        int _hook = EventWrapperHooks.onItemPickup((ItemEntity) (Object) this, player);
        hook.set(_hook);
        if (_hook < 0) {
            ci.cancel();
        }
    }
    @ModifyExpressionValue(
            method = "playerTouch",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z")
    )
    private boolean onCheckCanPickup(boolean original, @Local ItemStack itemStack, @Share("count") LocalIntRef i, @Share("hook") LocalIntRef hook) {
        return (hook.get() == 1 || i.get() <= 0 || original);
    }

    @Inject(
            method = "playerTouch",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;take(Lnet/minecraft/world/entity/Entity;I)V")
    )
    private void onPickup(Player player, CallbackInfo ci, @Local ItemStack itemStack, @Share("count") LocalIntRef i, @Local LocalIntRef originI) {
        ItemStack copy = itemStack.copy();
        copy.setCount(i.get());
        EventWrapperHooks.firePlayerItemPickupEvent(player, (ItemEntity) (Object) this, copy);
        originI.set(copy.getCount() - itemStack.getCount());
    }

}
