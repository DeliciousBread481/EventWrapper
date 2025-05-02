package io.github.lounode.eventwrapper.test.event.entity.living;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.eventwrapper.test.EventTest;
import io.github.lounode.eventwrapper.test.GameTestHolder;
import io.github.lounode.eventwrapper.test.TestUtil;
import net.minecraft.gametest.framework.AfterBatch;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

@GameTestHolder("eventwrapper")
@EventBusSubscriberWrapper
public class LivingEventTest implements EventTest {
    static final String BATCH = "event-entity-living-LivingEventWrapper";

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testJump(GameTestHelper helper) {
        var player = helper.makeMockPlayer();
        TestUtil.setName(player, "Jumping");
        player.jumpFromGround();
        TestUtil.assertName(player, "Jumping-success");
        helper.succeed();
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testLook(GameTestHelper helper) {
        var player = helper.makeMockPlayer();
        TestUtil.setName(player, BATCH + "Looking");
        TestUtil.assertEquals(0.0D, player.getVisibilityPercent(null));

        helper.succeed();
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testTick(GameTestHelper helper) {
        var player = helper.makeMockPlayer();

        TestUtil.setName(player, BATCH + "Ticking0");
        for (int i = 1; i<= 10 ;i++) {
            player.tick();
        }
        TestUtil.assertName(player, BATCH + "Ticking10");

        helper.succeed();
    }
    @SubscribeEventWrapper
    public static void onLivingJump(LivingEventWrapper.LivingJumpEvent event) {
        if (TestUtil.hasName(event.getEntity(), "Jumping")) {
            TestUtil.setName(event.getEntity(), "Jumping-success");
        }
    }

    @SubscribeEventWrapper
    public static void onLivingLook(LivingEventWrapper.LivingVisibilityEvent event) {
        if (TestUtil.hasName(event.getEntity(), BATCH + "Looking")) {
            event.modifyVisibility(0);
        }
    }

    @SubscribeEventWrapper
    public static void onLivingTick(LivingEventWrapper.LivingTickEvent event) {
        if (event.getEntity().getCustomName().getString().startsWith(BATCH + "Ticking")) {
            int count = Integer.parseInt(event.getEntity().getCustomName().getString().replace(BATCH + "Ticking", ""));
            count++;
            TestUtil.setName(event.getEntity(), BATCH + "Ticking" + count);
        }
    }
}
