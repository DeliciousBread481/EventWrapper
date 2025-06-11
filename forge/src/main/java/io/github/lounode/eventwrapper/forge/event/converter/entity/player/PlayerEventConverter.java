package io.github.lounode.eventwrapper.forge.event.converter.entity.player;

import net.minecraftforge.event.entity.player.PlayerEvent;


import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class PlayerEventConverter {
	public static class HarvestCheckConverter implements ForgeEventConverter<PlayerEvent.HarvestCheck, PlayerEventWrapper.HarvestCheck> {
		@Override
		public PlayerEventWrapper.HarvestCheck toWrapper(PlayerEvent.HarvestCheck event) {
			return new PlayerEventWrapper.HarvestCheck(event.getEntity(), event.getTargetBlock(), event.canHarvest());
		}

		@Override
		public PlayerEvent.HarvestCheck toEvent(PlayerEventWrapper.HarvestCheck wrapper) {
			return new PlayerEvent.HarvestCheck(wrapper.getEntity(), wrapper.getTargetBlock(), wrapper.canHarvest());
		}
	}

	public static class BreakSpeedConverter implements ForgeEventConverter<PlayerEvent.BreakSpeed, PlayerEventWrapper.BreakSpeed> {
		@Override
		public PlayerEventWrapper.BreakSpeed toWrapper(PlayerEvent.BreakSpeed event) {
			var wrapper = new PlayerEventWrapper.BreakSpeed(
					event.getEntity(),
					event.getState(),
					event.getOriginalSpeed(),
					event.getPosition().orElse(null)
			);
			wrapper.setCanceled(event.isCanceled());
			wrapper.setNewSpeed(event.getNewSpeed());
			return wrapper;
		}

		@Override
		public PlayerEvent.BreakSpeed toEvent(PlayerEventWrapper.BreakSpeed wrapper) {
			var event = new PlayerEvent.BreakSpeed(
					wrapper.getEntity(),
					wrapper.getState(),
					wrapper.getOriginalSpeed(),
					wrapper.getPosition().orElse(null)
			);
			event.setCanceled(event.isCanceled());
			event.setNewSpeed(wrapper.getNewSpeed());
			return event;
		}
	}

	public static class NameFormatConverter implements ForgeEventConverter<PlayerEvent.NameFormat, PlayerEventWrapper.NameFormat> {
		@Override
		public PlayerEventWrapper.NameFormat toWrapper(PlayerEvent.NameFormat event) {
			var wrapper = new PlayerEventWrapper.NameFormat(event.getEntity(), event.getUsername());
			wrapper.setDisplayname(event.getDisplayname());
			return wrapper;
		}

		@Override
		public PlayerEvent.NameFormat toEvent(PlayerEventWrapper.NameFormat wrapper) {
			var event = new PlayerEvent.NameFormat(wrapper.getEntity(), wrapper.getUsername());
			event.setDisplayname(wrapper.getDisplayname());
			return event;
		}
	}

	public static class TabListNameFormatConverter implements ForgeEventConverter<PlayerEvent.TabListNameFormat, PlayerEventWrapper.TabListNameFormat> {
		@Override
		public PlayerEventWrapper.TabListNameFormat toWrapper(PlayerEvent.TabListNameFormat event) {
			var wrapper = new PlayerEventWrapper.TabListNameFormat(event);
			wrapper.setDisplayName(event.getDisplayName());
			return wrapper;
		}

		@Override
		public PlayerEvent.TabListNameFormat toEvent(PlayerEventWrapper.TabListNameFormat wrapper) {
			var event = new PlayerEvent.TabListNameFormat(wrapper.getEntity());
			event.setDisplayName(wrapper.getDisplayName());
			return event;
		}
	}

	public static class CloneConverter implements ForgeEventConverter<PlayerEvent.Clone, PlayerEventWrapper.Clone> {
		@Override
		public PlayerEventWrapper.Clone toWrapper(PlayerEvent.Clone event) {
			return new PlayerEventWrapper.Clone(event.getEntity(), event.getOriginal(), event.isWasDeath());
		}

		@Override
		public PlayerEvent.Clone toEvent(PlayerEventWrapper.Clone wrapper) {
			return new PlayerEvent.Clone(wrapper.getEntity(), wrapper.getOriginal(), wrapper.isWasDeath());
		}
	}

	public static class StartTrackingConverter implements ForgeEventConverter<PlayerEvent.StartTracking, PlayerEventWrapper.StartTracking> {
		@Override
		public PlayerEventWrapper.StartTracking toWrapper(PlayerEvent.StartTracking event) {
			return new PlayerEventWrapper.StartTracking(event.getEntity(), event.getTarget());
		}

		@Override
		public PlayerEvent.StartTracking toEvent(PlayerEventWrapper.StartTracking wrapper) {
			return new PlayerEvent.StartTracking(wrapper.getEntity(), wrapper.getTarget());
		}
	}

	public static class StopTrackingConverter implements ForgeEventConverter<PlayerEvent.StopTracking, PlayerEventWrapper.StopTracking> {
		@Override
		public PlayerEventWrapper.StopTracking toWrapper(PlayerEvent.StopTracking event) {
			return new PlayerEventWrapper.StopTracking(event.getEntity(), event.getTarget());
		}

		@Override
		public PlayerEvent.StopTracking toEvent(PlayerEventWrapper.StopTracking wrapper) {
			return new PlayerEvent.StopTracking(wrapper.getEntity(), wrapper.getTarget());
		}
	}

	public static class LoadFromFileConverter implements ForgeEventConverter<PlayerEvent.LoadFromFile, PlayerEventWrapper.LoadFromFile> {
		@Override
		public PlayerEventWrapper.LoadFromFile toWrapper(PlayerEvent.LoadFromFile event) {
			return new PlayerEventWrapper.LoadFromFile(event.getEntity(), event.getPlayerDirectory(), event.getPlayerUUID());
		}

		@Override
		public PlayerEvent.LoadFromFile toEvent(PlayerEventWrapper.LoadFromFile wrapper) {
			return new PlayerEvent.LoadFromFile(wrapper.getEntity(), wrapper.getPlayerDirectory(), wrapper.getPlayerUUID());
		}
	}

	public static class SaveToFileConverter implements ForgeEventConverter<PlayerEvent.SaveToFile, PlayerEventWrapper.SaveToFile> {
		@Override
		public PlayerEventWrapper.SaveToFile toWrapper(PlayerEvent.SaveToFile event) {
			return new PlayerEventWrapper.SaveToFile(event.getEntity(), event.getPlayerDirectory(), event.getPlayerUUID());
		}

		@Override
		public PlayerEvent.SaveToFile toEvent(PlayerEventWrapper.SaveToFile wrapper) {
			return new PlayerEvent.SaveToFile(wrapper.getEntity(), wrapper.getPlayerDirectory(), wrapper.getPlayerUUID());
		}
	}

	public static class ItemPickupConverter implements ForgeEventConverter<PlayerEvent.ItemPickupEvent, PlayerEventWrapper.ItemPickupEvent> {
		@Override
		public PlayerEventWrapper.ItemPickupEvent toWrapper(PlayerEvent.ItemPickupEvent event) {
			return new PlayerEventWrapper.ItemPickupEvent(event.getEntity(), event.getOriginalEntity(), event.getStack());
		}

		@Override
		public PlayerEvent.ItemPickupEvent toEvent(PlayerEventWrapper.ItemPickupEvent wrapper) {
			return new PlayerEvent.ItemPickupEvent(wrapper.getEntity(), wrapper.getOriginalEntity(), wrapper.getStack());
		}
	}

	public static class ItemCraftedConverter implements ForgeEventConverter<PlayerEvent.ItemCraftedEvent, PlayerEventWrapper.ItemCraftedEvent> {
		@Override
		public PlayerEventWrapper.ItemCraftedEvent toWrapper(PlayerEvent.ItemCraftedEvent event) {
			return new PlayerEventWrapper.ItemCraftedEvent(event.getEntity(), event.getCrafting(), event.getInventory());
		}

		@Override
		public PlayerEvent.ItemCraftedEvent toEvent(PlayerEventWrapper.ItemCraftedEvent wrapper) {
			return new PlayerEvent.ItemCraftedEvent(wrapper.getEntity(), wrapper.getCrafting(), wrapper.getInventory());
		}
	}

	public static class ItemSmeltedConverter implements ForgeEventConverter<PlayerEvent.ItemSmeltedEvent, PlayerEventWrapper.ItemSmeltedEvent> {
		@Override
		public PlayerEventWrapper.ItemSmeltedEvent toWrapper(PlayerEvent.ItemSmeltedEvent event) {
			return new PlayerEventWrapper.ItemSmeltedEvent(event.getEntity(), event.getSmelting());
		}

		@Override
		public PlayerEvent.ItemSmeltedEvent toEvent(PlayerEventWrapper.ItemSmeltedEvent wrapper) {
			return new PlayerEvent.ItemSmeltedEvent(wrapper.getEntity(), wrapper.getSmelting());
		}
	}

	public static class PlayerLoggedInConverter implements ForgeEventConverter<PlayerEvent.PlayerLoggedInEvent, PlayerEventWrapper.PlayerLoggedInEvent> {
		@Override
		public PlayerEventWrapper.PlayerLoggedInEvent toWrapper(PlayerEvent.PlayerLoggedInEvent event) {
			return new PlayerEventWrapper.PlayerLoggedInEvent(event.getEntity());
		}

		@Override
		public PlayerEvent.PlayerLoggedInEvent toEvent(PlayerEventWrapper.PlayerLoggedInEvent wrapper) {
			return new PlayerEvent.PlayerLoggedInEvent(wrapper.getEntity());
		}
	}

	public static class PlayerLoggedOutConverter implements ForgeEventConverter<PlayerEvent.PlayerLoggedOutEvent, PlayerEventWrapper.PlayerLoggedOutEvent> {
		@Override
		public PlayerEventWrapper.PlayerLoggedOutEvent toWrapper(PlayerEvent.PlayerLoggedOutEvent event) {
			return new PlayerEventWrapper.PlayerLoggedOutEvent(event.getEntity());
		}

		@Override
		public PlayerEvent.PlayerLoggedOutEvent toEvent(PlayerEventWrapper.PlayerLoggedOutEvent wrapper) {
			return new PlayerEvent.PlayerLoggedOutEvent(wrapper.getEntity());
		}
	}

	public static class PlayerRespawnConverter implements ForgeEventConverter<PlayerEvent.PlayerRespawnEvent, PlayerEventWrapper.PlayerRespawnEvent> {
		@Override
		public PlayerEventWrapper.PlayerRespawnEvent toWrapper(PlayerEvent.PlayerRespawnEvent event) {
			return new PlayerEventWrapper.PlayerRespawnEvent(event.getEntity(), event.isEndConquered());
		}

		@Override
		public PlayerEvent.PlayerRespawnEvent toEvent(PlayerEventWrapper.PlayerRespawnEvent wrapper) {
			return new PlayerEvent.PlayerRespawnEvent(wrapper.getEntity(), wrapper.isEndConquered());
		}
	}

	public static class PlayerChangedDimensionConverter implements ForgeEventConverter<PlayerEvent.PlayerChangedDimensionEvent, PlayerEventWrapper.PlayerChangedDimensionEvent> {
		@Override
		public PlayerEventWrapper.PlayerChangedDimensionEvent toWrapper(PlayerEvent.PlayerChangedDimensionEvent event) {
			return new PlayerEventWrapper.PlayerChangedDimensionEvent(event.getEntity(), event.getFrom(), event.getTo());
		}

		@Override
		public PlayerEvent.PlayerChangedDimensionEvent toEvent(PlayerEventWrapper.PlayerChangedDimensionEvent wrapper) {
			return new PlayerEvent.PlayerChangedDimensionEvent(wrapper.getEntity(), wrapper.getFrom(), wrapper.getTo());
		}
	}

	public static class PlayerChangeGameModeConverter implements ForgeEventConverter<PlayerEvent.PlayerChangeGameModeEvent, PlayerEventWrapper.PlayerChangeGameModeEvent> {
		@Override
		public PlayerEventWrapper.PlayerChangeGameModeEvent toWrapper(PlayerEvent.PlayerChangeGameModeEvent event) {
			var wrapper = new PlayerEventWrapper.PlayerChangeGameModeEvent(event.getEntity(), event.getCurrentGameMode(), event.getNewGameMode());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}

		@Override
		public PlayerEvent.PlayerChangeGameModeEvent toEvent(PlayerEventWrapper.PlayerChangeGameModeEvent wrapper) {
			var event = new PlayerEvent.PlayerChangeGameModeEvent(wrapper.getEntity(), wrapper.getCurrentGameMode(), wrapper.getNewGameMode());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}
	}
}
