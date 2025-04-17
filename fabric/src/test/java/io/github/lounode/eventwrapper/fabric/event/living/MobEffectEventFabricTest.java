package io.github.lounode.eventwrapper.fabric.event.living;

import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class MobEffectEventFabricTest {

    @BeforeAll
    static void setup() {
        //EventFactory.register(this);
        //SharedConstants.createGameVersion();
        //Bootstrap.initialize();
    }

    @Test
    void testEventPost() {

    }

    @Test
    void testEventReceive() {

    }

    @SubscribeEventWrapper
    void onMobEffect(MobEffectEventWrapper event) {

    }

    void testEventModify() {

    }

    void testEventCancel() {

    }
}
