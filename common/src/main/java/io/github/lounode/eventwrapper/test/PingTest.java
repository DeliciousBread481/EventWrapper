package io.github.lounode.eventwrapper.test;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@GameTestHolder("eventwrapper")
public class PingTest implements EventTest {

    @GameTest(template = TestUtil.EMPTY_STRUCTURE)
    public void doTest(GameTestHelper helper) {
        LOGGER.info("Ping Test OK");
        helper.succeed();
    }
}
