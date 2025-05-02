package io.github.lounode.eventwrapper.fabric.event.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class MobEffectEventFabricTest {

    @BeforeAll
    static void setup() {
        //EventFactory.register(this);
        //SharedConstants.createGameVersion();
        //Bootstrap.initialize();
        //EventsWrapper.register(MobEffectEventFabricTest.class);
    }

    @Test
    void testEventPost() {
        //Zombie testZombie = new Zombie()
    }

    @Test
    void testEventReceive() {

    }

    @SubscribeEventWrapper
    void onMobEffect(MobEffectEventWrapper.Added event) {

    }

    void testEventModify() {

    }

    void testEventCancel() {

    }
}
