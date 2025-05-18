package io.github.lounode.eventwrapper.event.entity.player;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;


import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;

/**
 * This event is called when a player collides with a EntityItem on the ground.
 * The event can be canceled, and no further processing will be done.
 *
 * You can set the result of this event to ALLOW which will trigger the
 * processing of achievements, FML's event, play the sound, and kill the
 * entity if all the items are picked up.
 *
 * setResult(ALLOW) is the same as the old setHandled()
 */
@Cancelable
@EventWrapper.HasResult
public class EntityItemPickupEventWrapper extends PlayerEventWrapper {
	private final ItemEntity item;

	public EntityItemPickupEventWrapper(Player player, ItemEntity item) {
		super(player);
		this.item = item;
	}

	public ItemEntity getItem() {
		return item;
	}

	public EntityItemPickupEventWrapper(EntityItemPickupEvent event) {
		this(event.getEntity(), event.getItem());
	}

	public static Class<? extends Event> getForgeClass() {
		return EntityItemPickupEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new EntityItemPickupEvent(getEntity(), getItem());
	}
}
