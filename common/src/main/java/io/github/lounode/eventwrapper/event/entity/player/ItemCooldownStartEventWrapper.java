package io.github.lounode.eventwrapper.event.entity.player;

import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import io.github.lounode.eventwrapper.forge.event.entity.player.ItemCooldownStartEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ItemCooldownStartEventWrapper extends PlayerEventWrapper {
    private final Item item;
    private int ticks;

    public ItemCooldownStartEventWrapper(Player player, Item item, int ticks) {
        super(player);
        this.item = item;
        this.ticks = ticks;
    }

    public ItemCooldownStartEventWrapper(ItemCooldownStartEvent event) {
        this(event.getEntity(), event.getItem(), event.getTicks());
    }

    public Item getItem() {
        return item;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public static Class<? extends Event> getForgeClass() {
        return ItemCooldownStartEvent.class;
    }

    @Override
    public Object toForgeEvent() {
        return new ItemCooldownStartEvent(getEntity(), getItem(), getTicks());
    }
}
