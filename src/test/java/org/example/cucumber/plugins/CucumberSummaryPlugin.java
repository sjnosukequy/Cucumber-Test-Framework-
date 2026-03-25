package org.example.cucumber.plugins;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.Status;

import java.util.concurrent.atomic.AtomicInteger;
import static org.example.cucumber.utils.loggerUtils.LOGGER;
import org.example.cucumber.utils.loggerUtils;

public class CucumberSummaryPlugin implements ConcurrentEventListener {
    private final AtomicInteger total = new AtomicInteger();
    private final AtomicInteger passed = new AtomicInteger();
    private final AtomicInteger failed = new AtomicInteger();
    private final AtomicInteger skipped = new AtomicInteger();

    private long startNanos = 0L;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, event -> {
            startNanos = System.nanoTime();
        });

        publisher.registerHandlerFor(TestCaseFinished.class, event -> {
            total.incrementAndGet();

            Status status = event.getResult().getStatus();
            switch (status) {
                case PASSED:
                    passed.incrementAndGet();
                    break;
                case FAILED:
                    failed.incrementAndGet();
                    break;
                case SKIPPED:
                    skipped.incrementAndGet();
                    break;
                default:
                    break;
            }
        });

        publisher.registerHandlerFor(TestRunFinished.class, event -> {
            double elapsedSeconds = 0.0;
            if (startNanos > 0L) {
                elapsedSeconds = (System.nanoTime() - startNanos) / 1_000_000_000.0;
            }

            LOGGER.info("TEST SUMMARY");
            LOGGER.info(
                    "Tests run: {}, Failures: {}, Skipped: {}, Time elapsed: {} s",
                    total.get(),
                    failed.get(),
                    skipped.get(),
                    String.format("%.3f", elapsedSeconds));
            LOGGER.info("Passed: {}", passed.get());
            loggerUtils.newLine();
        });
    }
}
