package io.github.lounode.eventwrapper.forge.event.converter.entity;

import net.minecraftforge.event.entity.EntityEvent;


import io.github.lounode.eventwrapper.event.entity.EntityEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class EntityEventConverter {

	public static class EntityConstructingConverter implements ForgeEventConverter<EntityEvent.EntityConstructing, EntityEventWrapper.EntityConstructing> {
		@Override
		public EntityEventWrapper.EntityConstructing toWrapper(EntityEvent.EntityConstructing event) {
			return new EntityEventWrapper.EntityConstructing(event.getEntity());
		}

		@Override
		public EntityEvent.EntityConstructing toEvent(EntityEventWrapper.EntityConstructing wrapper) {
			return new EntityEvent.EntityConstructing(wrapper.getEntity());
		}
	}

	public static class EnteringSectionConverter implements ForgeEventConverter<EntityEvent.EnteringSection, EntityEventWrapper.EnteringSection> {
		@Override
		public EntityEventWrapper.EnteringSection toWrapper(EntityEvent.EnteringSection event) {
			return new EntityEventWrapper.EnteringSection(event.getEntity(), event.getPackedOldPos(), event.getPackedNewPos());
		}

		@Override
		public EntityEvent.EnteringSection toEvent(EntityEventWrapper.EnteringSection wrapper) {
			return new EntityEvent.EnteringSection(
					wrapper.getEntity(),
					wrapper.getPackedOldPos(),
					wrapper.getPackedNewPos()
			);
		}
	}
}
