package io.github.lounode.eventwrapper.event.entity.player;

import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.Event;


/**
 * AttackEntityEvent is fired when a player attacks an Entity.<br>
 * This event is fired whenever a player attacks an Entity in
 * {@link Player#attack(Entity)}.<br>
 * <br>
 * {@link #target} contains the Entity that was damaged by the player. <br>
 * <br>
 * This event is {@link Cancelable}.<br>
 * If this event is canceled, the player does not attack the Entity.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class AttackEntityEventWrapper extends PlayerEventWrapper {
    private final Entity target;
    public AttackEntityEventWrapper(Player player, Entity target)
    {
        super(player);
        this.target = target;
    }

    public Entity getTarget()
    {
        return target;
    }

    public AttackEntityEventWrapper(AttackEntityEvent event) {
        this(event.getEntity(), event.getTarget());
    }

    public static Class<? extends Event> getForgeClass() {
        return AttackEntityEvent.class;
    }

    @Override
    public Object toForgeEvent() {
        return new AttackEntityEvent(getEntity(), getTarget());
    }
}
