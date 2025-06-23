package io.github.lounode.eventwrapper.fabric.mixin.eventposter.play_level_sound_event;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(ClientLevel.class)
public class EventPosterClientLevel {

	@Inject(
		method = "playSeededSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onPlaySoundAtPos(@Nullable Player player, double x, double y, double z, Holder<SoundEvent> sound, SoundSource source, float volume, float pitch, long seed, CallbackInfo ci,
			@Local(argsOnly = true) LocalRef<Holder<SoundEvent>> soundRef,
			@Local(argsOnly = true) LocalRef<SoundSource> sourceRef,
			@Local(argsOnly = true, ordinal = 0) LocalFloatRef volumeRef,
			@Local(argsOnly = true, ordinal = 1) LocalFloatRef pitchRef) {
		var event = EventWrapperHooks.onPlaySoundAtPosition((ClientLevel) (Object) this, x, y, z, sound, source, volume, pitch);
		if (event.isCanceled() || event.getSound() == null) {
			ci.cancel();
			return;
		}

		soundRef.set(event.getSound());
		sourceRef.set(event.getSource());
		volumeRef.set(event.getNewVolume());
		pitchRef.set(event.getNewPitch());
	}

	@Inject(
		method = "playSeededSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private void onPlaySoundAtEntity(Player player, Entity entity, Holder<SoundEvent> sound, SoundSource category, float volume, float pitch, long seed, CallbackInfo ci,
			@Local(argsOnly = true) LocalRef<Holder<SoundEvent>> soundRef,
			@Local(argsOnly = true) LocalRef<SoundSource> sourceRef,
			@Local(argsOnly = true, ordinal = 0) LocalFloatRef volumeRef,
			@Local(argsOnly = true, ordinal = 1) LocalFloatRef pitchRef) {
		var event = EventWrapperHooks.onPlaySoundAtEntity(entity, sound, category, volume, pitch);
		if (event.isCanceled() || event.getSound() == null) {
			ci.cancel();
			return;
		}

		soundRef.set(event.getSound());
		sourceRef.set(event.getSource());
		volumeRef.set(event.getNewVolume());
		pitchRef.set(event.getNewPitch());
	}
}
