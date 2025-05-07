package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerinteractevent;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class PlayerInteractEventUseItemClientEventPoster {

    @Inject(
            method = "method_41929",//Lambda
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;"
            ),
            cancellable = true)
    private void onUseItem(InteractionHand interactionHand, Player player, MutableObject<InteractionResult> mutableObject, int i, CallbackInfoReturnable<Packet<?>> cir, @Local(argsOnly = true) InteractionHand hand, @Local ServerboundUseItemPacket serverbounduseitempacket) {
        InteractionResult cancelResult = EventWrapperHooks.onItemRightClick(player, hand);
        if (cancelResult != null) {
            mutableObject.setValue(cancelResult);
            cir.setReturnValue(serverbounduseitempacket);
        }
    }
}
