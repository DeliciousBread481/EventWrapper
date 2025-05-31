package io.github.lounode.eventwrapper.fabric.mixin.eventposter.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
import java.util.WeakHashMap;

import io.github.lounode.eventwrapper.fabric.EventWrapperHooks;

@Mixin(AnvilMenu.class)
public abstract class AnvilRepairEventPoster extends ItemCombinerMenu {

	protected AnvilRepairEventPoster(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
		super(MenuType.ANVIL, containerId, playerInventory, access);
	}

	@Unique
	private final static WeakHashMap<UUID, Float> _eventWrapperBreakChance = new WeakHashMap<>();

	@Inject(
		method = "onTake",
		at = @At("HEAD")
	)
	private void onTake(Player player, ItemStack stack, CallbackInfo ci) {
		float chance = EventWrapperHooks.onAnvilRepair(player, stack, this.inputSlots.getItem(0), this.inputSlots.getItem(1));
		_eventWrapperBreakChance.put(player.getUUID(), chance);
	}

	@Inject(
		method = "method_24922",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void onExecute(Player player, Level level, BlockPos blockPos, CallbackInfo ci) {
		final float TARGET_CHANCE = 0.12F;
		final float EPSILON = 0.001F;
		float breakChance = TARGET_CHANCE;
		if (_eventWrapperBreakChance.containsKey(player.getUUID())) {
			breakChance = _eventWrapperBreakChance.get(player.getUUID());
			_eventWrapperBreakChance.remove(player.getUUID());
		}

		if (!_floatEquals(breakChance, TARGET_CHANCE, EPSILON)) {
			BlockState blockState = level.getBlockState(blockPos);
			if (!player.getAbilities().instabuild && blockState.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < breakChance) {
				BlockState blockState2 = AnvilBlock.damage(blockState);
				if (blockState2 == null) {
					level.removeBlock(blockPos, false);
					level.levelEvent(1029, blockPos, 0);
				} else {
					level.setBlock(blockPos, blockState2, 2);
					level.levelEvent(1030, blockPos, 0);
				}
			} else {
				level.levelEvent(1030, blockPos, 0);
			}
			ci.cancel();
		}
	}

	@Unique
	private static boolean _floatEquals(float a, float b, float epsilon) {
		return Math.abs(a - b) < epsilon;
	}
}
