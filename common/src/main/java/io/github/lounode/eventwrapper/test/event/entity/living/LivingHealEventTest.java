package io.github.lounode.eventwrapper.test.event.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.LivingHealEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.test.EventTest;
import io.github.lounode.eventwrapper.test.GameTestHolder;
import io.github.lounode.eventwrapper.test.TestUtil;
import net.minecraft.gametest.framework.AfterBatch;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Zombie;

@GameTestHolder("eventwrapper")
@EventBusSubscriberWrapper
public class LivingHealEventTest implements EventTest {
    static final String BATCH = "event-entity-living-LivingHealEventWrapper";
    static boolean cancel;
    static boolean modify;
    @BeforeBatch(batch = BATCH)
    public void beforeEach(ServerLevel level) {
        cancel = false;
        modify = false;
    }

    @AfterBatch(batch = BATCH)
    public void afterEach(ServerLevel level) {
        cancel = false;
        modify = false;
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testPlayerHeal(GameTestHelper helper) {
        var player = helper.makeMockPlayer();

        player.setHealth(10);
        TestUtil.assertEquals(10.0f, player.getHealth());

        player.heal(2);
        TestUtil.assertEquals(12.0f, player.getHealth());

        modify = true;
        player.heal(2);
        TestUtil.assertEquals(16.0f, player.getHealth());
        modify = false;

        cancel = true;
        player.heal(2);
        TestUtil.assertEquals(16.0f, player.getHealth());
        cancel = false;


        helper.succeed();
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testMobHeal(GameTestHelper helper) {
        var zombie = new Zombie(helper.getLevel());

        zombie.setHealth(10);
        TestUtil.assertEquals(10.0f, zombie.getHealth());

        zombie.heal(2);
        TestUtil.assertEquals(12.0f, zombie.getHealth());

        modify = true;
        zombie.heal(2);
        TestUtil.assertEquals(16.0f, zombie.getHealth());
        modify = false;

        cancel = true;
        zombie.heal(2);
        TestUtil.assertEquals(16.0f, zombie.getHealth());
        cancel = false;


        helper.succeed();
    }

    @SubscribeEventWrapper
    public static void onLivingHeal(LivingHealEventWrapper event) {
        if (cancel) {
            event.setCanceled(true);
        } else if (modify) {
            event.setAmount(event.getAmount() + 2.0F);
        }
    }
}
