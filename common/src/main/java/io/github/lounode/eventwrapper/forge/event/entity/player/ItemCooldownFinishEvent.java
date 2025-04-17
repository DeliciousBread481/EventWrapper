package io.github.lounode.eventwrapper.forge.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ItemCooldownFinishEvent extends PlayerEvent {
    private final Item item;

    public ItemCooldownFinishEvent(Player player, Item item) {
        super(player);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
