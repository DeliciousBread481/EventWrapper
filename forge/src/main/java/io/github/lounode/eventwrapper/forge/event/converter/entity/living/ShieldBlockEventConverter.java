package io.github.lounode.eventwrapper.forge.event.converter.entity.living;

import net.minecraftforge.event.entity.living.ShieldBlockEvent;


import io.github.lounode.eventwrapper.event.entity.living.ShieldBlockEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class ShieldBlockEventConverter implements ForgeEventConverter<ShieldBlockEvent, ShieldBlockEventWrapper> {
	@Override
	public ShieldBlockEvent toEvent(ShieldBlockEventWrapper wrapper) {
		var event = new ShieldBlockEvent(wrapper.getEntity(), wrapper.getDamageSource(), wrapper.getOriginalBlockedDamage());
		event.setCanceled(wrapper.isCanceled());
		event.setBlockedDamage(wrapper.getBlockedDamage());
		event.setShieldTakesDamage(wrapper.shieldTakesDamage());
		return event;
	}

	@Override
	public ShieldBlockEventWrapper toWrapper(ShieldBlockEvent event) {
		var wrapper = new ShieldBlockEventWrapper(event.getEntity(), event.getDamageSource(), event.getOriginalBlockedDamage());
		wrapper.setCanceled(event.isCanceled());
		wrapper.setBlockedDamage(event.getBlockedDamage());
		wrapper.setShieldTakesDamage(event.shieldTakesDamage());
		return wrapper;
	}
}
