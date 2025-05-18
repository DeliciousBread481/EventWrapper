package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(PlayerList.class)
public class PlayerEventLogoutEventPoster {

	@Inject(
		method = "remove",
		at = @At("HEAD")
	)
	private void onPlayerLogout(ServerPlayer player, CallbackInfo ci) {
		EventWrapperHooks.firePlayerLoggedOut(player);
	}
}
