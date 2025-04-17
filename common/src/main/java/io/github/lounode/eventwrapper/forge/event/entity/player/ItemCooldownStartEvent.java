package io.github.lounode.eventwrapper.forge.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class ItemCooldownStartEvent extends PlayerEvent {
    private final Item item;
    private int ticks;
    public ItemCooldownStartEvent(Player player, Item item, int ticks) {
        super(player);
        this.item = item;
        this.ticks = ticks;
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
}
