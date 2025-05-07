package io.github.lounode.eventwrapper.fabric;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerInteractEventWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EventWrapperHooks {

    public static InteractionResult onInteractEntityAt(Player player, Entity entity, HitResult ray, InteractionHand hand)
    {
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

    public static InteractionResult onItemRightClick(Player player, InteractionHand hand)
    {
        PlayerInteractEventWrapper.RightClickItem evt = new PlayerInteractEventWrapper.RightClickItem(player, hand);
        EventsWrapper.post(evt);
        return evt.isCanceled() ? evt.getCancellationResult() : null;
    }

    //clint 3
    //server 1
    public static PlayerInteractEventWrapper.LeftClickBlock onLeftClickBlock(Player player, BlockPos pos, Direction face, ServerboundPlayerActionPacket.Action action)
    {
        PlayerInteractEventWrapper.LeftClickBlock evt = new PlayerInteractEventWrapper.LeftClickBlock(player, pos, face, PlayerInteractEventWrapper.LeftClickBlock.Action.convert(action));
        EventsWrapper.post(evt);
        return evt;
    }

    public static PlayerInteractEventWrapper.LeftClickBlock onClientMineHold(Player player, BlockPos pos, Direction face)
    {
        PlayerInteractEventWrapper.LeftClickBlock evt = new PlayerInteractEventWrapper.LeftClickBlock(player, pos, face, PlayerInteractEventWrapper.LeftClickBlock.Action.CLIENT_HOLD);
        EventsWrapper.post(evt);
        return evt;
    }

    public static PlayerInteractEventWrapper.RightClickBlock onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec)
    {
        PlayerInteractEventWrapper.RightClickBlock evt = new PlayerInteractEventWrapper.RightClickBlock(player, hand, pos, hitVec);
        EventsWrapper.post(evt);
        return evt;
    }

    public static void onEmptyClick(Player player, InteractionHand hand)
    {
        EventsWrapper.post(new PlayerInteractEventWrapper.RightClickEmpty(player, hand));
    }

    public static void onEmptyLeftClick(Player player)
    {
        EventsWrapper.post(new PlayerInteractEventWrapper.LeftClickEmpty(player));
    }
}
