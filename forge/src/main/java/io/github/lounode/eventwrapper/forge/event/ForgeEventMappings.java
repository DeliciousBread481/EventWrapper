package io.github.lounode.eventwrapper.forge.event;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.Event;

import org.jetbrains.annotations.Nullable;


import io.github.lounode.eventwrapper.event.PlayLevelSoundEventWrapper;
import io.github.lounode.eventwrapper.event.entity.EntityEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.*;
import io.github.lounode.eventwrapper.event.entity.player.*;
import io.github.lounode.eventwrapper.event.furnace.FurnaceFuelBurnTimeEventWrapper;
import io.github.lounode.eventwrapper.event.server.*;
import io.github.lounode.eventwrapper.eventbus.api.EventConverter;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
import io.github.lounode.eventwrapper.forge.event.converter.ForgeEventConverter;
import io.github.lounode.eventwrapper.forge.event.converter.PlayLevelSoundEventConverter;
import io.github.lounode.eventwrapper.forge.event.converter.entity.EntityEventConverter;
import io.github.lounode.eventwrapper.forge.event.converter.entity.living.*;
import io.github.lounode.eventwrapper.forge.event.converter.entity.player.*;
import io.github.lounode.eventwrapper.forge.event.converter.furnace.FurnaceFuelBurnTimeEventConverter;
import io.github.lounode.eventwrapper.forge.event.converter.server.*;
import io.github.lounode.eventwrapper.forge.event.entity.player.ItemCooldownFinishEvent;
import io.github.lounode.eventwrapper.forge.event.entity.player.ItemCooldownStartEvent;

public class ForgeEventMappings {
	private static final BiMap<Class<? extends Event>, Class<? extends EventWrapper>> MAPPINGS = HashBiMap.create();
	private static final BiMap<Class<? extends Event>, ForgeEventConverter<?, ?>> EVENT_TO_WRAPPER_MAPPINGS = HashBiMap.create();
	private static final BiMap<Class<? extends EventWrapper>, ForgeEventConverter<?, ?>> WRAPPER_TO_EVENT_MAPPINGS = HashBiMap.create();

	public static void makeLink(Class<? extends Event> event, Class<? extends EventWrapper> wrapper, ForgeEventConverter<?, ?> converter) {
		MAPPINGS.put(event, wrapper);
		EVENT_TO_WRAPPER_MAPPINGS.put(event, converter);
		WRAPPER_TO_EVENT_MAPPINGS.put(wrapper, converter);
	}

	@Nullable
	public static EventConverter<?, ?> getConverter(Class<?> clazz) {
		if (EventWrapper.class.isAssignableFrom(clazz)) {
			return WRAPPER_TO_EVENT_MAPPINGS.get(clazz);
		} else if (Event.class.isAssignableFrom(clazz)) {
			return EVENT_TO_WRAPPER_MAPPINGS.get(clazz);
		}
		return null;
	}

	@Nullable
	public static Class<? extends Event> getForgeEventClass(Class<? extends EventWrapper> wrapperClass) {
		return MAPPINGS.inverse().get(wrapperClass);
	}

	@Nullable
	public static Class<? extends EventWrapper> getWrapperClass(Class<? extends Event> eventClass) {
		return MAPPINGS.get(eventClass);
	}

	static {
		//ServerLifeCycle Event
		makeLink(ServerLifecycleEvent.class, ServerLifecycleEventWrapper.class, new ServerLifecycleEventConverter());
		makeLink(ServerAboutToStartEvent.class, ServerAboutToStartEventWrapper.class, new ServerAboutToStartEventConverter());
		makeLink(ServerStartedEvent.class, ServerStartedEventWrapper.class, new ServerStartedEventConverter());
		makeLink(ServerStartingEvent.class, ServerStartingEventWrapper.class, new ServerStartingEventConverter());
		makeLink(ServerStoppedEvent.class, ServerStoppedEventWrapper.class, new ServerStoppedEventConverter());
		makeLink(ServerStoppingEvent.class, ServerStoppingEventWrapper.class, new ServerStoppingEventConverter());
		makeLink(FurnaceFuelBurnTimeEvent.class, FurnaceFuelBurnTimeEventWrapper.class, new FurnaceFuelBurnTimeEventConverter());
		// Entity Events
		makeLink(EntityEvent.EntityConstructing.class, EntityEventWrapper.EntityConstructing.class, new EntityEventConverter.EntityConstructingConverter());
		makeLink(EntityEvent.EnteringSection.class, EntityEventWrapper.EnteringSection.class, new EntityEventConverter.EnteringSectionConverter());

		//Player
		makeLink(AnvilRepairEvent.class, AnvilRepairEventWrapper.class, new AnvilRepairEventConverter());
		makeLink(AttackEntityEvent.class, AttackEntityEventWrapper.class, new AttackEntityEventConverter());
		makeLink(EntityItemPickupEvent.class, EntityItemPickupEventWrapper.class, new EntityItemPickupEventConverter());
		makeLink(ItemCooldownStartEvent.class, ItemCooldownStartEventWrapper.class, new ItemCooldownStartEventConverter());
		makeLink(ItemCooldownFinishEvent.class, ItemCooldownFinishEventWrapper.class, new ItemCooldownFinishEventConverter());

		makeLink(PlayerEvent.HarvestCheck.class, PlayerEventWrapper.HarvestCheck.class, new PlayerEventConverter.HarvestCheckConverter());
		makeLink(PlayerEvent.BreakSpeed.class, PlayerEventWrapper.BreakSpeed.class, new PlayerEventConverter.BreakSpeedConverter());
		makeLink(PlayerEvent.NameFormat.class, PlayerEventWrapper.NameFormat.class, new PlayerEventConverter.NameFormatConverter());
		makeLink(PlayerEvent.TabListNameFormat.class, PlayerEventWrapper.TabListNameFormat.class, new PlayerEventConverter.TabListNameFormatConverter());
		makeLink(PlayerEvent.Clone.class, PlayerEventWrapper.Clone.class, new PlayerEventConverter.CloneConverter());
		makeLink(PlayerEvent.StartTracking.class, PlayerEventWrapper.StartTracking.class, new PlayerEventConverter.StartTrackingConverter());
		makeLink(PlayerEvent.StopTracking.class, PlayerEventWrapper.StopTracking.class, new PlayerEventConverter.StopTrackingConverter());
		makeLink(PlayerEvent.LoadFromFile.class, PlayerEventWrapper.LoadFromFile.class, new PlayerEventConverter.LoadFromFileConverter());
		makeLink(PlayerEvent.SaveToFile.class, PlayerEventWrapper.SaveToFile.class, new PlayerEventConverter.SaveToFileConverter());
		makeLink(PlayerEvent.ItemPickupEvent.class, PlayerEventWrapper.ItemPickupEvent.class, new PlayerEventConverter.ItemPickupConverter());
		makeLink(PlayerEvent.ItemCraftedEvent.class, PlayerEventWrapper.ItemCraftedEvent.class, new PlayerEventConverter.ItemCraftedConverter());
		makeLink(PlayerEvent.ItemSmeltedEvent.class, PlayerEventWrapper.ItemSmeltedEvent.class, new PlayerEventConverter.ItemSmeltedConverter());
		makeLink(PlayerEvent.PlayerLoggedInEvent.class, PlayerEventWrapper.PlayerLoggedInEvent.class, new PlayerEventConverter.PlayerLoggedInConverter());
		makeLink(PlayerEvent.PlayerLoggedOutEvent.class, PlayerEventWrapper.PlayerLoggedOutEvent.class, new PlayerEventConverter.PlayerLoggedOutConverter());
		makeLink(PlayerEvent.PlayerRespawnEvent.class, PlayerEventWrapper.PlayerRespawnEvent.class, new PlayerEventConverter.PlayerRespawnConverter());
		makeLink(PlayerEvent.PlayerChangedDimensionEvent.class, PlayerEventWrapper.PlayerChangedDimensionEvent.class, new PlayerEventConverter.PlayerChangedDimensionConverter());
		makeLink(PlayerEvent.PlayerChangeGameModeEvent.class, PlayerEventWrapper.PlayerChangeGameModeEvent.class, new PlayerEventConverter.PlayerChangeGameModeConverter());

		makeLink(PlayerInteractEvent.EntityInteractSpecific.class, PlayerInteractEventWrapper.EntityInteractSpecific.class, new PlayerInteractEventConverter.EntityInteractSpecificConverter());
		makeLink(PlayerInteractEvent.EntityInteract.class, PlayerInteractEventWrapper.EntityInteract.class, new PlayerInteractEventConverter.EntityInteractConverter());
		makeLink(PlayerInteractEvent.RightClickBlock.class, PlayerInteractEventWrapper.RightClickBlock.class, new PlayerInteractEventConverter.RightClickBlockConverter());
		makeLink(PlayerInteractEvent.RightClickItem.class, PlayerInteractEventWrapper.RightClickItem.class, new PlayerInteractEventConverter.RightClickItemConverter());
		makeLink(PlayerInteractEvent.RightClickEmpty.class, PlayerInteractEventWrapper.RightClickEmpty.class, new PlayerInteractEventConverter.RightClickEmptyConverter());
		makeLink(PlayerInteractEvent.LeftClickBlock.class, PlayerInteractEventWrapper.LeftClickBlock.class, new PlayerInteractEventConverter.LeftClickBlockConverter());
		makeLink(PlayerInteractEvent.LeftClickEmpty.class, PlayerInteractEventWrapper.LeftClickEmpty.class, new PlayerInteractEventConverter.LeftClickEmptyConverter());
		//Living
		makeLink(LivingAttackEvent.class, LivingAttackEventWrapper.class, new LivingAttackEventConverter());
		makeLink(LivingDamageEvent.class, LivingDamageEventWrapper.class, new LivingDamageEventConverter());
		makeLink(LivingHealEvent.class, LivingHealEventWrapper.class, new LivingHealEventConverter());
		makeLink(LivingHurtEvent.class, LivingHurtEventWrapper.class, new LivingHurtEventConverter());
		makeLink(MobEffectEvent.Remove.class, MobEffectEventWrapper.Remove.class, new MobEffectEventConverter.RemoveConverter());
		makeLink(MobEffectEvent.Applicable.class, MobEffectEventWrapper.Applicable.class, new MobEffectEventConverter.ApplicableConverter());
		makeLink(MobEffectEvent.Added.class, MobEffectEventWrapper.Added.class, new MobEffectEventConverter.AddedConverter());
		makeLink(MobEffectEvent.Expired.class, MobEffectEventWrapper.Expired.class, new MobEffectEventConverter.ExpiredConverter());
		makeLink(LivingEvent.LivingJumpEvent.class, LivingEventWrapper.LivingJumpEvent.class, new LivingEventConverter.LivingJumpConverter());
		makeLink(LivingEvent.LivingTickEvent.class, LivingEventWrapper.LivingTickEvent.class, new LivingEventConverter.LivingTickConverter());
		makeLink(LivingEvent.LivingVisibilityEvent.class, LivingEventWrapper.LivingVisibilityEvent.class, new LivingEventConverter.LivingVisibilityConverter());

		//Other
		makeLink(PlayLevelSoundEvent.AtEntity.class, PlayLevelSoundEventWrapper.AtEntity.class, new PlayLevelSoundEventConverter.AtEntity());
		makeLink(PlayLevelSoundEvent.AtPosition.class, PlayLevelSoundEventWrapper.AtPosition.class, new PlayLevelSoundEventConverter.AtPosition());
	}
}
