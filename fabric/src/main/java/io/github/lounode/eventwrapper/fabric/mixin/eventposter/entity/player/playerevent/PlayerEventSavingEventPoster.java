package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player.playerevent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(PlayerDataStorage.class)
public class PlayerEventSavingEventPoster {

	@Shadow
	@Final
	public File playerDir;

	@Inject(
		method = "save",
		at = @At("RETURN")
	)
	private void onSaving(Player player, CallbackInfo ci) {
		EventWrapperHooks.firePlayerSavingEvent(player, this.playerDir, player.getStringUUID());
	}
}
