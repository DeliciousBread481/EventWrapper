package io.github.lounode.eventwrapper.test.event.entity.living;

import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventWrapper;
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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

@GameTestHolder("eventwrapper")
@EventBusSubscriberWrapper
public class MobEffectEventTest implements EventTest {

    static final String BATCH = "event-entity-living-MobEffectEventWrapper";

    static boolean add;
    static boolean remove;
    static boolean apply;
    static boolean tick;
    static int tickTime;

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testAdd(GameTestHelper helper) {
        add = true;
        Level world = helper.getLevel();

        var player = helper.makeMockSurvivalPlayer();
        var effect = new MobEffectInstance(MobEffects.BAD_OMEN, 20);
        player.addEffect(effect);
        TestUtil.assertEquals("ADDED", player.getCustomName().getString());
        //TestUtil.assertNotHaveEffect(player, MobEffects.BAD_OMEN);
        helper.succeed();
        add = false;
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testRemove(GameTestHelper helper) {
        var player = helper.makeMockSurvivalPlayer();
        var effect = new MobEffectInstance(MobEffects.LUCK, 20);
        var effect2 = new MobEffectInstance(MobEffects.UNLUCK, 20);

        player.addEffect(effect);
        player.addEffect(effect2);


        TestUtil.assertHaveEffects(player, MobEffects.LUCK, MobEffects.UNLUCK);


        player.removeEffect(MobEffects.LUCK);
        TestUtil.assertNotHaveEffect(player, MobEffects.LUCK);
        player.addEffect(effect);

        player.removeAllEffects();
        TestUtil.assertNotHaveEffects(player, MobEffects.LUCK, MobEffects.UNLUCK);
        //
        remove = true;
        player.addEffect(effect);
        player.addEffect(effect2);

        player.removeAllEffects();
        TestUtil.assertHaveEffect(player, MobEffects.UNLUCK);

        player.removeEffect(MobEffects.UNLUCK);
        TestUtil.assertHaveEffect(player, MobEffects.UNLUCK);
        remove = false;

        player.removeEffect(MobEffects.UNLUCK);
        TestUtil.assertNotHaveEffect(player, MobEffects.UNLUCK);

        player.removeAllEffects();
        TestUtil.assertNotHaveEffects(player, MobEffects.UNLUCK, MobEffects.LUCK);



        helper.succeed();
    }
    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testApply(GameTestHelper helper) {
        var level = helper.getLevel();
        var player = helper.makeMockSurvivalPlayer();
        var spider = new CaveSpider(EntityType.CAVE_SPIDER, level);

        var poison = new MobEffectInstance(MobEffects.POISON, 20);
        var luck = new MobEffectInstance(MobEffects.SLOW_FALLING, 20);

        player.addEffect(poison);
        spider.addEffect(poison);

        TestUtil.assertHaveEffect(player, MobEffects.POISON);
        TestUtil.assertNotHaveEffect(spider, MobEffects.POISON);

        player.removeAllEffects();
        spider.removeAllEffects();

        apply = true;

        player.addEffect(luck);
        spider.addEffect(poison);

        TestUtil.assertHaveEffect(spider, MobEffects.POISON);
        TestUtil.assertNotHaveEffect(player, MobEffects.SLOW_FALLING);
        apply = false;
        helper.succeed();
    }

    @GameTest(
            batch = BATCH,
            template = TestUtil.EMPTY_STRUCTURE
    )
    public void testTick(GameTestHelper helper) {
        var player = helper.makeMockSurvivalPlayer();
        var luck = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20);

        player.addEffect(luck);

        tick = true;
        while (tick && tickTime < 10) {
            luck.tick(player, ()->{});
        }

        helper.succeed();
    }
    @SubscribeEventWrapper
    public static void onEffectAdd(MobEffectEventWrapper.Added wrapper) {
        if (add) {
            wrapper.getEntity().setCustomName(Component.literal("ADDED"));
        }
    }

    @SubscribeEventWrapper
    public static void onEffectRemove(MobEffectEventWrapper.Remove wrapper) {
        if (remove) {
            if (wrapper.getEffect() == MobEffects.UNLUCK) {
                wrapper.setCanceled(true);
            }
        }
    }

    @SubscribeEventWrapper
    public static void onEffectApplicable(MobEffectEventWrapper.Applicable wrapper) {
        if (apply) {
            if (wrapper.getEffectInstance().getEffect() == MobEffects.POISON) {
                wrapper.setResult(EventWrapper.Result.ALLOW);
            }
            if (wrapper.getEffectInstance().getEffect() == MobEffects.SLOW_FALLING) {
                wrapper.setResult(EventWrapper.Result.DENY);
            }
        }
    }
    @SubscribeEventWrapper
    public static void onEffectTick(MobEffectEventWrapper.Expired wrapper) {
        if (tick) {
            if (wrapper.getEffectInstance().getEffect() == MobEffects.DIG_SLOWDOWN) {
                tickTime+=1;
                if (tickTime >= 10) {
                    tick = false;
                }
            }
        }
    }
}
