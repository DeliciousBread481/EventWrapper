package io.github.lounode.eventwrapper.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;


import io.github.lounode.eventwrapper.forge.event.entity.player.ItemCooldownFinishEvent;

public class ItemCooldownFinishEventWrapper extends PlayerEventWrapper {
	private final Item item;

	public ItemCooldownFinishEventWrapper(Player player, Item item) {
		super(player);
		this.item = item;
	}

	public ItemCooldownFinishEventWrapper(ItemCooldownFinishEvent event) {
		this(event.getEntity(), event.getItem());
	}

	public Item getItem() {
		return item;
	}

	public static Class<? extends Event> getForgeClass() {
		return ItemCooldownFinishEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new ItemCooldownFinishEvent(getEntity(), getItem());
	}
}
