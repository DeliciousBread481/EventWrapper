package io.github.lounode.eventwrapper.test;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameTestHolder("eventwrapper")
public class PingTest implements EventTest {

    @GameTest(template = TestUtil.EMPTY_STRUCTURE)
    public void doTest(GameTestHelper helper) {
        LOGGER.info("Ping Test OK");
        helper.succeed();
    }
}
