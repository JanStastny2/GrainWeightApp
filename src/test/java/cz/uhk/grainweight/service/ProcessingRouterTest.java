package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.processing.PoolStrategy;
import cz.uhk.grainweight.model.processing.ProcessingMode;
import cz.uhk.grainweight.model.processing.SerialStrategy;
import cz.uhk.grainweight.model.processing.VirtualStrategy;
import cz.uhk.grainweight.model.processing.WorkSpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProcessingRouterTest {

    @Test
    void pick_WhenModeSerial_ShouldReturnSerialStrategy() {
        SerialStrategy serial = mock(SerialStrategy.class);
        PoolStrategy pool = mock(PoolStrategy.class);
        VirtualStrategy virtual = mock(VirtualStrategy.class);
        ProcessingRouter router = new ProcessingRouter(serial, pool, virtual);

        assertSame(serial, router.pick(new WorkSpec(ProcessingMode.SERIAL, null)));
    }

    @Test
    void pick_WhenModePoolWithSize_ShouldReturnPoolStrategyAndApplyCap() {
        SerialStrategy serial = mock(SerialStrategy.class);
        PoolStrategy pool = mock(PoolStrategy.class);
        VirtualStrategy virtual = mock(VirtualStrategy.class);
        ProcessingRouter router = new ProcessingRouter(serial, pool, virtual);

        assertSame(pool, router.pick(new WorkSpec(ProcessingMode.POOL, 4)));
        verify(pool).setCap(4);
    }

    @Test
    void pick_WhenModeVirtualWithSize_ShouldReturnVirtualStrategyAndApplyCap() {
        SerialStrategy serial = mock(SerialStrategy.class);
        PoolStrategy pool = mock(PoolStrategy.class);
        VirtualStrategy virtual = mock(VirtualStrategy.class);
        ProcessingRouter router = new ProcessingRouter(serial, pool, virtual);

        assertSame(virtual, router.pick(new WorkSpec(ProcessingMode.VIRTUAL, 6)));
        verify(virtual).setConcurrencyCap(6);
    }
}
