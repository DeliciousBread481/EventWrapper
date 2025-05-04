package io.github.lounode.eventwrapper.test.event.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.LivingAttackEventWrapper;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

@GameTestHolder("eventwrapper")
@EventBusSubscriberWrapper
public class LivingAttackEventTest implements EventTest {
    static final String BATCH = "event-entity-living-LivingAttackEventWrapper";
    static boolean cancel;
    static boolean listen;

    @BeforeBatch(batch = BATCH)
    public void beforeEach(ServerLevel level) {
        cancel = false;
        listen = false;
    }

    @AfterBatch(batch = BATCH)
    public void afterEach(ServerLevel level) {
        cancel = false;
        listen = false;
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testListen(GameTestHelper helper) {
        listen = true;
        Level world = helper.getLevel();

        var player = helper.makeMockSurvivalPlayer();
        var zombie = new Zombie(world);

        player.hurt(zombie.level().damageSources().mobAttack(zombie), 2.0F);
        TestUtil.assertEquals("ATK", zombie.getCustomName().getString());
        TestUtil.assertEquals("DEF", player.getCustomName().getString());

        helper.succeed();
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testCancel(GameTestHelper helper) {
        cancel = true;
        Level world = helper.getLevel();

        var player = helper.makeMockSurvivalPlayer();
        var zombie = new Zombie(world);


        zombie.hurt(player.level().damageSources().playerAttack(player), 2.0F);
        TestUtil.assertEquals(20.0F, zombie.getHealth());

        helper.succeed();
    }

    @SubscribeEventWrapper
    public static void onLivingAttack(LivingAttackEventWrapper wrapper) {
        if (listen) {
            wrapper.getEntity().setCustomName(Component.literal("DEF"));
            if (wrapper.getSource().is(DamageTypes.MOB_ATTACK)) {
                wrapper.getSource().getEntity().setCustomName(Component.literal("ATK"));
            }
        }
        if (cancel) {
            wrapper.setCanceled(true);
        }
    }
}
