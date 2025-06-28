package io.github.lounode.eventwrapper.forge.event.converter.level;

import net.minecraft.util.random.WeightedRandomList;
import net.minecraftforge.event.level.LevelEvent;


import io.github.lounode.eventwrapper.event.level.LevelEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class LevelEventConverter {
	public static class Load implements ForgeEventConverter<LevelEvent.Load, LevelEventWrapper.Load> {

		@Override
		public LevelEvent.Load toEvent(LevelEventWrapper.Load wrapper) {
			return new LevelEvent.Load(wrapper.getLevel());
		}

		@Override
		public LevelEventWrapper.Load toWrapper(LevelEvent.Load event) {
			return new LevelEventWrapper.Load(event.getLevel());
		}
	}

	public static class Unload implements ForgeEventConverter<LevelEvent.Unload, LevelEventWrapper.Unload> {

		@Override
		public LevelEvent.Unload toEvent(LevelEventWrapper.Unload wrapper) {
			return new LevelEvent.Unload(wrapper.getLevel());
		}

		@Override
		public LevelEventWrapper.Unload toWrapper(LevelEvent.Unload event) {
			return new LevelEventWrapper.Unload(event.getLevel());
		}
	}

	public static class Save implements ForgeEventConverter<LevelEvent.Save, LevelEventWrapper.Save> {

		@Override
		public LevelEvent.Save toEvent(LevelEventWrapper.Save wrapper) {
			return new LevelEvent.Save(wrapper.getLevel());
		}

		@Override
		public LevelEventWrapper.Save toWrapper(LevelEvent.Save event) {
			return new LevelEventWrapper.Save(event.getLevel());
		}
	}

	public static class CreateSpawnPosition implements ForgeEventConverter<LevelEvent.CreateSpawnPosition, LevelEventWrapper.CreateSpawnPosition> {

		@Override
		public LevelEvent.CreateSpawnPosition toEvent(LevelEventWrapper.CreateSpawnPosition wrapper) {
			var event = new LevelEvent.CreateSpawnPosition(wrapper.getLevel(), wrapper.getSettings());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}

		@Override
		public LevelEventWrapper.CreateSpawnPosition toWrapper(LevelEvent.CreateSpawnPosition event) {
			var wrapper = new LevelEventWrapper.CreateSpawnPosition(event.getLevel(), event.getSettings());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}
	}

	public static class PotentialSpawns implements ForgeEventConverter<LevelEvent.PotentialSpawns, LevelEventWrapper.PotentialSpawns> {

		@Override
		public LevelEvent.PotentialSpawns toEvent(LevelEventWrapper.PotentialSpawns wrapper) {
			var listNow = WeightedRandomList.create(wrapper.getSpawnerDataList());
			var event = new LevelEvent.PotentialSpawns(wrapper.getLevel(), wrapper.getMobCategory(), wrapper.getPos(), listNow);
			event.setCanceled(wrapper.isCanceled());
			return event;
		}

		@Override
		public LevelEventWrapper.PotentialSpawns toWrapper(LevelEvent.PotentialSpawns event) {
			var listNow = WeightedRandomList.create(event.getSpawnerDataList());
			var wrapper = new LevelEventWrapper.PotentialSpawns(event.getLevel(), event.getMobCategory(), event.getPos(), listNow);
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}
	}
}
