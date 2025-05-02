package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class PlayerEventBreakSpeedEventPoster {

    @Inject(
            method = "getDestroyProgress",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"),
            cancellable = true
    )
    private void onGetDigSpeed(BlockState state, Player player, BlockGetter level, BlockPos pos,
                               CallbackInfoReturnable<Float> cir,
                               @Local float f,
                               @Local int i
    ) {
        float originSpeed = player.getDestroySpeed(state);

        PlayerEventWrapper.BreakSpeed event = new PlayerEventWrapper.BreakSpeed(
                player,
                state,
                originSpeed,
                pos
        );
        EventsWrapper.post(event);

        cir.setReturnValue(event.getNewSpeed() / f / (float)i);
    }
}
