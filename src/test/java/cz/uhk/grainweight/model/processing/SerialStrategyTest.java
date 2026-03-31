package cz.uhk.grainweight.model.processing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SerialStrategyTest {

    private SerialStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SerialStrategy();
    }

    @Test
    void execute_ShouldReturnTaskResult() {
        ProcessingResult<String> result = strategy.execute(() -> "hello");

        assertNotNull(result);
        assertEquals("hello", result.getData());
    }

    @Test
    void execute_ShouldMeasureServerProcessingMs() {
        int delayMs = 50;

        ProcessingResult<String> result = strategy.execute(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "done";
        });

        assertTrue(result.getServerProcessingMs() >= delayMs - 10,
                "serverProcessingMs should be at least " + (delayMs - 10) + " ms, was: " + result.getServerProcessingMs());
    }

    @Test
    void execute_ShouldEnforceSerialExecution_OnlyOneTaskAtATime() throws InterruptedException {
        int threadCount = 5;
        AtomicInteger concurrentlyRunning = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        CountDownLatch allDone = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    strategy.execute(() -> {
                        int current = concurrentlyRunning.incrementAndGet();
                        maxConcurrent.accumulateAndGet(current, Math::max);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        concurrentlyRunning.decrementAndGet();
                        return "ok";
                    });
                } finally {
                    allDone.countDown();
                }
            });
        }

        assertTrue(allDone.await(10, TimeUnit.SECONDS), "Tasks did not complete in time");
        executor.shutdown();

        assertEquals(1, maxConcurrent.get(),
                "SerialStrategy must never run more than 1 task concurrently, max was: " + maxConcurrent.get());
    }

    @Test
    void execute_ShouldPropagate_RuntimeException() {
        assertThrows(RuntimeException.class, () ->
                strategy.execute(() -> { throw new RuntimeException("task error"); })
        );
    }
}
