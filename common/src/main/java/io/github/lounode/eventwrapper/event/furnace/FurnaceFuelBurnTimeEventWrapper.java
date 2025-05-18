package io.github.lounode.eventwrapper.event.furnace;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.Event;

import org.jetbrains.annotations.Nullable;


import io.github.lounode.eventwrapper.eventbus.api.Cancelable;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;

/**
 * {@link FurnaceFuelBurnTimeEventWrapper} is fired when determining the fuel value for an ItemStack. <br>
 * <br>
 * To set the burn time of your own item, use {@link Item#getBurnTime(ItemStack, RecipeType)} instead.<br>
 * <br>
 * This event is fired from {@link ForgeEventFactory#getItemBurnTime(ItemStack, int, RecipeType)}.<br>
 * <br>
 * This event is {@link Cancelable} to prevent later handlers from changing the value.<br>
 * <br>
 * This event does not have a result. {@link EventWrapper.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class FurnaceFuelBurnTimeEventWrapper extends EventWrapper {
	private final ItemStack itemStack;
	@Nullable
	private final RecipeType<?> recipeType;
	private int burnTime;

	public FurnaceFuelBurnTimeEventWrapper(ItemStack itemStack, int burnTime, @Nullable RecipeType<?> recipeType) {
		this.itemStack = itemStack;
		this.burnTime = burnTime;
		this.recipeType = recipeType;
	}

	/**
	 * Get the ItemStack "fuel" in question.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 *
	 * Get the recipe type for which to obtain the burn time, if known.
	 */
	@Nullable
	public RecipeType<?> getRecipeType() {
		return recipeType;
	}

	/**
	 * Set the burn time for the given ItemStack.
	 * Setting it to 0 will prevent the item from being used as fuel, overriding vanilla's decision.
	 */
	public void setBurnTime(int burnTime) {
		if (burnTime >= 0) {
			this.burnTime = burnTime;
			setCanceled(true);
		}
	}

	/**
	 * The resulting value of this event, the burn time for the ItemStack.
	 * A value of 0 will prevent the item from being used as fuel, overriding vanilla's decision.
	 */
	public int getBurnTime() {
		return burnTime;
	}

	public FurnaceFuelBurnTimeEventWrapper(FurnaceFuelBurnTimeEvent event) {
		this(event.getItemStack(), event.getBurnTime(), event.getRecipeType());
	}

	public static Class<? extends Event> getForgeClass() {
		return FurnaceFuelBurnTimeEvent.class;
	}

	@Override
	public Object toForgeEvent() {
		return new FurnaceFuelBurnTimeEvent(getItemStack(), getBurnTime(), getRecipeType());
	}
}
