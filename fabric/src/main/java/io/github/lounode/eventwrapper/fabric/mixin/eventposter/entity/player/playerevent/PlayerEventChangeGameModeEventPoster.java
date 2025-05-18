package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ServerPlayer.class)
public abstract class PlayerEventChangeGameModeEventPoster {

	@Shadow
	@Final
	public ServerPlayerGameMode gameMode;

	@Inject(
		method = "setGameMode",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onSetGameMode(GameType gameMode, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) LocalRef<GameType> gameModeArg) {
		var mode = EventWrapperHooks.onChangeGameType((ServerPlayer) (Object) this, this.gameMode.getGameModeForPlayer(), gameMode);
		gameModeArg.set(mode);
		if (mode == null) {
			cir.setReturnValue(false);
		}
	}
}
