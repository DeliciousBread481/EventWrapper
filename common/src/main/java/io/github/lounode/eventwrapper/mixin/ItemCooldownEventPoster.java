package io.github.lounode.eventwrapper.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ServerItemCooldowns;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownFinishEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownStartEventWrapper;
import io.github.lounode.eventwrapper.utils.ClientUtil;

@Mixin(ItemCooldowns.class)
public class ItemCooldownEventPoster {

	@Inject(
		method = "removeCooldown",
		at = @At("HEAD")
	)
	private void onCooldownRemove(Item item, CallbackInfo ci) {
		ItemCooldowns self = (ItemCooldowns) (Object) this;
		Player player;
		if (!isServer(self)) {
			player = ClientUtil.getClientPlayer();
		} else {
			player = getServerPlayerField(self);
		}

		ItemCooldownFinishEventWrapper event = new ItemCooldownFinishEventWrapper(player, item);
		EventsWrapper.post(event);
	}

	@ModifyVariable(
		method = "addCooldown",
		at = @At("HEAD"),
		ordinal = 0,
		argsOnly = true
	)
	private int onCooldownStart(int ticks, Item item) {
		ItemCooldowns self = (ItemCooldowns) (Object) this;
		Player player;
		if (!isServer(self)) {
			player = ClientUtil.getClientPlayer();
		} else {
			player = getServerPlayerField(self);
		}
		ItemCooldownStartEventWrapper event = new ItemCooldownStartEventWrapper(player, item, ticks);
		EventsWrapper.post(event);
		return event.isCanceled() ? 0 : event.getTicks();
	}

	private boolean isServer(ItemCooldowns itemcooldowns) {
		return itemcooldowns instanceof ServerItemCooldowns;
	}

	private ServerPlayer getServerPlayerField(ItemCooldowns itemcooldowns) {
		ServerItemCooldowns cooldowns = (ServerItemCooldowns) itemcooldowns;

		for (Field field : cooldowns.getClass().getDeclaredFields()) {
			if (field.getType() == ServerPlayer.class) {
				try {
					field.setAccessible(true);
					return (ServerPlayer) field.get(cooldowns);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
