package io.github.lounode.eventwrapper.fabric.mixin.eventposter.play_level_sound_event;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(LocalPlayer.class)
public class EventPosterLocalPlayer {

	@Inject(
		method = "playSound",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onPlaySound(SoundEvent sound, float volume, float pitch, CallbackInfo ci, @Share("playSound") LocalRef<SoundSource> s,
			@Local(argsOnly = true) LocalRef<SoundEvent> soundRef,
			@Local(argsOnly = true, ordinal = 0) LocalFloatRef volumeRef,
			@Local(argsOnly = true, ordinal = 1) LocalFloatRef pitchRef) {
		var holder = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound);
		var player = (LocalPlayer) (Object) this;
		var event = EventWrapperHooks.onPlaySoundAtEntity(player, holder, player.getSoundSource(), volume, pitch);

		if (event.isCanceled() || event.getSound() == null) {
			ci.cancel();
			return;
		}

		soundRef.set(event.getSound().value());
		s.set(event.getSource());
		volumeRef.set(event.getNewVolume());
		pitchRef.set(event.getNewPitch());
	}

	@ModifyArgs(
		method = "playSound",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V")
	)
	private void onGetSoundSource(Args args, @Share("playSound") LocalRef<SoundSource> s) {
		if (s == null) {
			return;
		}

		args.set(4, s.get());
	}
	/*
	
	@ModifyArgs(
			method = "playSound",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V")
	)
	private void onPlaySound2(Args args, @Share("playAtEntity2") LocalRef<PlayLevelSoundEventWrapper.AtEntity> e) {
		if (e == null) {
			return;
		}
	
		args.set(0, e.get().getSound());
		//TODO Set Source
		args.set(1, e.get().getNewVolume());
		args.set(2, e.get().getNewPitch());
	}
	
	*/
}
