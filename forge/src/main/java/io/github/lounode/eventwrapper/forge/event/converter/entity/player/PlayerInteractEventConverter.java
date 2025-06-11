package io.github.lounode.eventwrapper.forge.event.converter.entity.player;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;


import io.github.lounode.eventwrapper.event.entity.player.*;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;

public class PlayerInteractEventConverter {

	public static class EntityInteractSpecificConverter implements ForgeEventConverter<PlayerInteractEvent.EntityInteractSpecific, PlayerInteractEventWrapper.EntityInteractSpecific> {
		@Override
		public PlayerInteractEventWrapper.EntityInteractSpecific toWrapper(PlayerInteractEvent.EntityInteractSpecific event) {
			var wrapper = new PlayerInteractEventWrapper.EntityInteractSpecific(event.getEntity(), event.getHand(), event.getTarget(), event.getLocalPos());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}

		@Override
		public PlayerInteractEvent.EntityInteractSpecific toEvent(PlayerInteractEventWrapper.EntityInteractSpecific wrapper) {
			var event = new PlayerInteractEvent.EntityInteractSpecific(wrapper.getEntity(), wrapper.getHand(), wrapper.getTarget(), wrapper.getLocalPos());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}
	}

	public static class EntityInteractConverter implements ForgeEventConverter<PlayerInteractEvent.EntityInteract, PlayerInteractEventWrapper.EntityInteract> {
		@Override
		public PlayerInteractEventWrapper.EntityInteract toWrapper(PlayerInteractEvent.EntityInteract event) {
			var wrapper = new PlayerInteractEventWrapper.EntityInteract(event.getEntity(), event.getHand(), event.getTarget());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}

		@Override
		public PlayerInteractEvent.EntityInteract toEvent(PlayerInteractEventWrapper.EntityInteract wrapper) {
			var event = new PlayerInteractEvent.EntityInteract(wrapper.getEntity(), wrapper.getHand(), wrapper.getTarget());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}
	}

	public static class RightClickBlockConverter implements ForgeEventConverter<PlayerInteractEvent.RightClickBlock, PlayerInteractEventWrapper.RightClickBlock> {
		@Override
		public PlayerInteractEventWrapper.RightClickBlock toWrapper(PlayerInteractEvent.RightClickBlock event) {
			var wrapper = new PlayerInteractEventWrapper.RightClickBlock(event.getEntity(), event.getHand(), event.getPos(), event.getHitVec());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}

		@Override
		public PlayerInteractEvent.RightClickBlock toEvent(PlayerInteractEventWrapper.RightClickBlock wrapper) {
			var event = new PlayerInteractEvent.RightClickBlock(wrapper.getEntity(), wrapper.getHand(), wrapper.getPos(), wrapper.getHitVec());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}
	}

	public static class RightClickItemConverter implements ForgeEventConverter<PlayerInteractEvent.RightClickItem, PlayerInteractEventWrapper.RightClickItem> {
		@Override
		public PlayerInteractEventWrapper.RightClickItem toWrapper(PlayerInteractEvent.RightClickItem event) {
			var wrapper = new PlayerInteractEventWrapper.RightClickItem(event.getEntity(), event.getHand());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}

		@Override
		public PlayerInteractEvent.RightClickItem toEvent(PlayerInteractEventWrapper.RightClickItem wrapper) {
			var event = new PlayerInteractEvent.RightClickItem(wrapper.getEntity(), wrapper.getHand());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}
	}

	public static class RightClickEmptyConverter implements ForgeEventConverter<PlayerInteractEvent.RightClickEmpty, PlayerInteractEventWrapper.RightClickEmpty> {
		@Override
		public PlayerInteractEventWrapper.RightClickEmpty toWrapper(PlayerInteractEvent.RightClickEmpty event) {
			return new PlayerInteractEventWrapper.RightClickEmpty(event.getEntity(), event.getHand());
		}

		@Override
		public PlayerInteractEvent.RightClickEmpty toEvent(PlayerInteractEventWrapper.RightClickEmpty wrapper) {
			return new PlayerInteractEvent.RightClickEmpty(wrapper.getEntity(), wrapper.getHand());
		}
	}

	public static class LeftClickBlockConverter implements ForgeEventConverter<PlayerInteractEvent.LeftClickBlock, PlayerInteractEventWrapper.LeftClickBlock> {
		@Override
		public PlayerInteractEventWrapper.LeftClickBlock toWrapper(PlayerInteractEvent.LeftClickBlock event) {
			var wrapper = new PlayerInteractEventWrapper.LeftClickBlock(event.getEntity(), event.getPos(), event.getFace());
			wrapper.setCanceled(event.isCanceled());
			return wrapper;
		}

		@Override
		public PlayerInteractEvent.LeftClickBlock toEvent(PlayerInteractEventWrapper.LeftClickBlock wrapper) {
			var event = new PlayerInteractEvent.LeftClickBlock(wrapper.getEntity(), wrapper.getPos(), wrapper.getFace());
			event.setCanceled(wrapper.isCanceled());
			return event;
		}
	}

	public static class LeftClickEmptyConverter implements ForgeEventConverter<PlayerInteractEvent.LeftClickEmpty, PlayerInteractEventWrapper.LeftClickEmpty> {
		@Override
		public PlayerInteractEventWrapper.LeftClickEmpty toWrapper(PlayerInteractEvent.LeftClickEmpty event) {
			return new PlayerInteractEventWrapper.LeftClickEmpty(event.getEntity());
		}

		@Override
		public PlayerInteractEvent.LeftClickEmpty toEvent(PlayerInteractEventWrapper.LeftClickEmpty wrapper) {
			return new PlayerInteractEvent.LeftClickEmpty(wrapper.getEntity());
		}
	}
}
