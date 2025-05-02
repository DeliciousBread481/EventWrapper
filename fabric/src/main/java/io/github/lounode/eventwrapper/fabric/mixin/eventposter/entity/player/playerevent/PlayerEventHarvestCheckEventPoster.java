package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEventHarvestCheckEventPoster {

    @Inject(
            method = "hasCorrectToolForDrops",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onHarvestCheck(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        PlayerEventWrapper.HarvestCheck event = new PlayerEventWrapper.HarvestCheck((Player) (Object) this, state, cir.getReturnValue());

        EventsWrapper.post(event);

        cir.setReturnValue(event.canHarvest());
    }
}
