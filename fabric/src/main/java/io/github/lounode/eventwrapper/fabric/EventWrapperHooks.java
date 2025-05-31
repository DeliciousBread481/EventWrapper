package io.github.lounode.eventwrapper.fabric;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.AnvilRepairEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.EntityItemPickupEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerInteractEventWrapper;
import io.github.lounode.eventwrapper.event.furnace.FurnaceFuelBurnTimeEventWrapper;
import io.github.lounode.eventwrapper.event.server.*;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;

public class EventWrapperHooks {

	public static InteractionResult onInteractEntityAt(Player player, Entity entity, HitResult ray, InteractionHand hand) {
		Vec3 vec3d = ray.getLocation().subtract(entity.position());
		return onInteractEntityAt(player, entity, vec3d, hand);
	}

	public static InteractionResult onInteractEntityAt(Player player, Entity entity, Vec3 vec3d, InteractionHand hand) {
		PlayerInteractEventWrapper.EntityInteractSpecific wrapper =
				new PlayerInteractEventWrapper.EntityInteractSpecific(player, hand, entity, vec3d);
		EventsWrapper.post(wrapper);
		return wrapper.isCanceled() ? wrapper.getCancellationResult() : null;
	}

	public static InteractionResult onInteractEntity(Player player, Entity entity, InteractionHand hand) {
		PlayerInteractEventWrapper.EntityInteract wrapper =
				new PlayerInteractEventWrapper.EntityInteract(player, hand, entity);
		EventsWrapper.post(wrapper);
		return wrapper.isCanceled() ? wrapper.getCancellationResult() : null;
	}

	public static InteractionResult onItemRightClick(Player player, InteractionHand hand) {
		PlayerInteractEventWrapper.RightClickItem evt = new PlayerInteractEventWrapper.RightClickItem(player, hand);
		EventsWrapper.post(evt);
		return evt.isCanceled() ? evt.getCancellationResult() : null;
	}

	//clint 3
	//server 1
	public static PlayerInteractEventWrapper.LeftClickBlock onLeftClickBlock(Player player, BlockPos pos, Direction face, ServerboundPlayerActionPacket.Action action) {
		PlayerInteractEventWrapper.LeftClickBlock evt = new PlayerInteractEventWrapper.LeftClickBlock(player, pos, face);
		EventsWrapper.post(evt);
		return evt;
	}
	/*
	public static PlayerInteractEventWrapper.LeftClickBlock onClientMineHold(Player player, BlockPos pos, Direction face)
	{
		PlayerInteractEventWrapper.LeftClickBlock evt = new PlayerInteractEventWrapper.LeftClickBlock(player, pos, face, PlayerInteractEventWrapper.LeftClickBlock.Action.CLIENT_HOLD);
		EventsWrapper.post(evt);
		return evt;
	}
	
	*/

	public static PlayerInteractEventWrapper.RightClickBlock onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) {
		PlayerInteractEventWrapper.RightClickBlock evt = new PlayerInteractEventWrapper.RightClickBlock(player, hand, pos, hitVec);
		EventsWrapper.post(evt);
		return evt;
	}

	public static void onEmptyClick(Player player, InteractionHand hand) {
		EventsWrapper.post(new PlayerInteractEventWrapper.RightClickEmpty(player, hand));
	}

	public static void onEmptyLeftClick(Player player) {
		EventsWrapper.post(new PlayerInteractEventWrapper.LeftClickEmpty(player));
	}

	public static boolean isCorrectToolForDrops(@NotNull BlockState state, @NotNull Player player) {
		PlayerEventWrapper.HarvestCheck event = new PlayerEventWrapper.HarvestCheck(player, state, true);
		EventsWrapper.post(event);
		return event.canHarvest();
	}

	public static Component getPlayerDisplayName(Player player, Component username) {
		PlayerEventWrapper.NameFormat event = new PlayerEventWrapper.NameFormat(player, username);
		EventsWrapper.post(event);
		return event.getDisplayname();
	}

	public static Component getPlayerTabListDisplayName(Player player) {
		PlayerEventWrapper.TabListNameFormat event = new PlayerEventWrapper.TabListNameFormat(player);
		EventsWrapper.post(event);
		return event.getDisplayName();
	}

	public static void onPlayerClone(Player player, Player oldPlayer, boolean wasDeath) {
		EventsWrapper.post(new PlayerEventWrapper.Clone(player, oldPlayer, wasDeath));
	}

	public static void onStartEntityTracking(Entity entity, Player player) {
		EventsWrapper.post(new PlayerEventWrapper.StartTracking(player, entity));
	}

	public static void onStopEntityTracking(Entity entity, Player player) {
		EventsWrapper.post(new PlayerEventWrapper.StopTracking(player, entity));
	}

	public static void firePlayerLoadingEvent(Player player, File playerDirectory, String uuidString) {
		EventsWrapper.post(new PlayerEventWrapper.LoadFromFile(player, playerDirectory, uuidString));
	}

	public static void firePlayerLoadingEvent(Player player, PlayerDataStorage playerFileData, String uuidString) {
		EventsWrapper.post(new PlayerEventWrapper.LoadFromFile(player, playerFileData.playerDir, uuidString));
	}

	public static void firePlayerSavingEvent(Player player, File playerDirectory, String uuidString) {
		EventsWrapper.post(new PlayerEventWrapper.SaveToFile(player, playerDirectory, uuidString));
	}

	public static int onItemPickup(ItemEntity entityItem, Player player) {
		var event = new EntityItemPickupEventWrapper(player, entityItem);
		if (event.isCanceled()) {
			return -1;
		}
		return event.getResult() == EventWrapper.Result.ALLOW ? 1 : 0;
	}

	public static void firePlayerItemPickupEvent(Player player, ItemEntity item, ItemStack clone) {
		EventsWrapper.post(new PlayerEventWrapper.ItemPickupEvent(player, item, clone));
	}

	public static void firePlayerCraftingEvent(Player player, ItemStack crafted, Container craftMatrix) {
		EventsWrapper.post(new PlayerEventWrapper.ItemCraftedEvent(player, crafted, craftMatrix));
	}

	public static void firePlayerSmeltedEvent(Player player, ItemStack smelted) {
		EventsWrapper.post(new PlayerEventWrapper.ItemSmeltedEvent(player, smelted));
	}

	public static void firePlayerChangedDimensionEvent(Player player, ResourceKey<Level> fromDim, ResourceKey<Level> toDim) {
		EventsWrapper.post(new PlayerEventWrapper.PlayerChangedDimensionEvent(player, fromDim, toDim));
	}

	public static void firePlayerLoggedIn(Player player) {
		EventsWrapper.post(new PlayerEventWrapper.PlayerLoggedInEvent(player));
	}

	public static void firePlayerLoggedOut(Player player) {
		EventsWrapper.post(new PlayerEventWrapper.PlayerLoggedOutEvent(player));
	}

	public static void firePlayerRespawnEvent(Player player, boolean endConquered) {
		EventsWrapper.post(new PlayerEventWrapper.PlayerRespawnEvent(player, endConquered));
	}

	public static @Nullable GameType onChangeGameType(Player player, GameType currentGameType, GameType newGameType) {
		if (currentGameType != newGameType) {
			var evt = new PlayerEventWrapper.PlayerChangeGameModeEvent(player, currentGameType, newGameType);
			EventsWrapper.post(evt);
			return evt.isCanceled() ? null : evt.getNewGameMode();
		} else {
			return newGameType;
		}
	}

	public static int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
		if (stack.isEmpty()) {
			return 0;
		}
		Item item = stack.getItem();
		var map = ((FuelRegistryImpl) FuelRegistry.INSTANCE).getFuelTimes();
		Integer burnTime = map.getOrDefault(item, null);
		int time = burnTime == null ? 0 : burnTime;
		return getItemBurnTime(stack, time, recipeType);
	}

	public static int getItemBurnTime(@NotNull ItemStack itemStack, int burnTime, @Nullable RecipeType<?> recipeType) {
		FurnaceFuelBurnTimeEventWrapper event = new FurnaceFuelBurnTimeEventWrapper(itemStack, burnTime, recipeType);
		EventsWrapper.post(event);

		Item item = itemStack.getItem();
		if (event.isCanceled()) {
			FuelRegistry.INSTANCE.remove(item);
			FuelRegistry.INSTANCE.add(item, event.getBurnTime());
		}

		return event.getBurnTime();
	}

	public static void onServerStarting(MinecraftServer minecraftServer) {
		EventsWrapper.post(new ServerStartingEventWrapper(minecraftServer));
	}

	public static void onServerStarted(MinecraftServer minecraftServer) {
		EventsWrapper.post(new ServerStartedEventWrapper(minecraftServer));
	}

	public static void onServerStopping(MinecraftServer minecraftServer) {
		EventsWrapper.post(new ServerStoppingEventWrapper(minecraftServer));
	}

	public static void onServerStopped(MinecraftServer minecraftServer) {
		EventsWrapper.post(new ServerStoppedEventWrapper(minecraftServer));
	}

	public static void onServerAboutToStart(MinecraftServer minecraftServer) {
		EventsWrapper.post(new ServerAboutToStartEventWrapper(minecraftServer));
	}

	public static float onAnvilRepair(Player player, @NotNull ItemStack output, @NotNull ItemStack left, @NotNull ItemStack right) {
		AnvilRepairEventWrapper e = new AnvilRepairEventWrapper(player, left, right, output);
		EventsWrapper.post(e);
		return e.getBreakChance();
	}
}
