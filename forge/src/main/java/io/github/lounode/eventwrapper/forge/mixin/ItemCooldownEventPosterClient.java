package io.github.lounode.eventwrapper.forge.mixin;  
  
import net.minecraft.world.entity.player.Player;  
import net.minecraft.world.item.Item;  
import net.minecraft.world.item.ItemCooldowns;  
import net.minecraft.world.item.ServerItemCooldowns;  
  
import org.spongepowered.asm.mixin.Mixin;  
import org.spongepowered.asm.mixin.injection.At;  
import org.spongepowered.asm.mixin.injection.Inject;  
import org.spongepowered.asm.mixin.injection.ModifyVariable;  
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;  
  
import io.github.lounode.eventwrapper.EventsWrapper;  
import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownFinishEventWrapper;  
import io.github.lounode.eventwrapper.event.entity.player.ItemCooldownStartEventWrapper;  
import io.github.lounode.eventwrapper.utils.ClientUtil;  
  
@Mixin(ItemCooldowns.class)  
public class ItemCooldownEventPosterClient {  
  
	@Inject(  
		method = "removeCooldown",  
		at = @At("HEAD")  
	)  
	private void onCooldownRemove(Item item, CallbackInfo ci) {  
		ItemCooldowns self = (ItemCooldowns) (Object) this;  
		Player player;  
		if (self instanceof ServerItemCooldowns cooldowns) {  
			player = cooldowns.player;  
		} else {  
			player = ClientUtil.getClientPlayer();  
		}  
  
		ItemCooldownFinishEventWrapper event = new ItemCooldownFinishEventWrapper(player, item);  
		EventsWrapper.post(event);  
	}  
  
	@ModifyVariable(  
		method = "addCooldown",  
		at = @At("HEAD"),  
		ordinal = 0,  
		argsOnly = true  
	)  
	private int onCooldownStart(int ticks, Item item) {  
		ItemCooldowns self = (ItemCooldowns) (Object) this;  
		Player player;  
		if (self instanceof ServerItemCooldowns cooldowns) {  
			player = cooldowns.player;  
		} else {  
			player = ClientUtil.getClientPlayer();  
		}  
		ItemCooldownStartEventWrapper event = new ItemCooldownStartEventWrapper(player, item, ticks);  
		EventsWrapper.post(event);  
		return event.isCanceled() ? 0 : event.getTicks();  
	}  
  
}