package io.github.lounode.eventwrapper.mixin;  
  
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
  
@Mixin(ItemCooldowns.class)  
public class ItemCooldownEventPoster {  
  
	@Inject(  
		method = "removeCooldown",  
		at = @At("HEAD")  
	)  
	private void onCooldownRemove(Item item, CallbackInfo ci) {  
		ItemCooldowns self = (ItemCooldowns) (Object) this;  
		if (!(self instanceof ServerItemCooldowns cooldowns)) {  
			return;
		}  
		Player player = cooldowns.player;  
		  
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
		if (!(self instanceof ServerItemCooldowns cooldowns)) {  
			return ticks;
		}  
		Player player = cooldowns.player;  
		ItemCooldownStartEventWrapper event = new ItemCooldownStartEventWrapper(player, item, ticks);  
		EventsWrapper.post(event);  
		return event.isCanceled() ? 0 : event.getTicks();  
	}  
}