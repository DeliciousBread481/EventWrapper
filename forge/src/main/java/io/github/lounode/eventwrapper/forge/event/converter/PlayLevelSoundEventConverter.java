package io.github.lounode.eventwrapper.forge.event.converter;

import net.minecraftforge.event.PlayLevelSoundEvent;


import io.github.lounode.eventwrapper.event.PlayLevelSoundEventWrapper;

public class PlayLevelSoundEventConverter {
	public static class AtEntity implements ForgeEventConverter<PlayLevelSoundEvent.AtEntity, PlayLevelSoundEventWrapper.AtEntity> {

		@Override
		public PlayLevelSoundEvent.AtEntity toEvent(PlayLevelSoundEventWrapper.AtEntity wrapper) {
			var event = new PlayLevelSoundEvent.AtEntity(
					wrapper.getEntity(),
					wrapper.getSound(),
					wrapper.getSource(),
					wrapper.getOriginalVolume(),
					wrapper.getOriginalPitch()
			);
			event.setNewVolume(wrapper.getNewVolume());
			event.setNewPitch(wrapper.getNewPitch());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}

		@Override
		public PlayLevelSoundEventWrapper.AtEntity toWrapper(PlayLevelSoundEvent.AtEntity event) {
			var wrapper = new PlayLevelSoundEventWrapper.AtEntity(
					event.getEntity(),
					event.getSound(),
					event.getSource(),
					event.getOriginalVolume(),
					event.getOriginalPitch()
			);
			wrapper.setNewVolume(event.getNewVolume());
			wrapper.setNewPitch(event.getNewPitch());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}
	}

	public static class AtPosition implements ForgeEventConverter<PlayLevelSoundEvent.AtPosition, PlayLevelSoundEventWrapper.AtPosition> {

		@Override
		public PlayLevelSoundEvent.AtPosition toEvent(PlayLevelSoundEventWrapper.AtPosition wrapper) {
			var event = new PlayLevelSoundEvent.AtPosition(
					wrapper.getLevel(),
					wrapper.getPosition(),
					wrapper.getSound(),
					wrapper.getSource(),
					wrapper.getOriginalVolume(),
					wrapper.getOriginalPitch()
			);
			event.setNewVolume(wrapper.getNewVolume());
			event.setNewPitch(wrapper.getNewPitch());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}

		@Override
		public PlayLevelSoundEventWrapper.AtPosition toWrapper(PlayLevelSoundEvent.AtPosition event) {
			var wrapper = new PlayLevelSoundEventWrapper.AtPosition(
					event.getLevel(),
					event.getPosition(),
					event.getSound(),
					event.getSource(),
					event.getOriginalVolume(),
					event.getOriginalPitch()
			);
			wrapper.setNewVolume(event.getNewVolume());
			wrapper.setNewPitch(event.getNewPitch());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}
	}
}
