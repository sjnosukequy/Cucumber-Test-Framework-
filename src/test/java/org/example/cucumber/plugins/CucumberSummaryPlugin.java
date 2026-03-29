package org.example.cucumber.plugins;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.Status;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import static org.example.cucumber.utils.loggerUtils.LOGGER;
// import org.example.cucumber.utils.loggerUtils;
import io.cucumber.plugin.event.TestCase;

public class CucumberSummaryPlugin implements ConcurrentEventListener {
    private final AtomicInteger total = new AtomicInteger();
    private final AtomicInteger pass = new AtomicInteger();
    private final AtomicInteger failed = new AtomicInteger();
    private final AtomicInteger skipped = new AtomicInteger();

    private final List<String> failedTestCases = new CopyOnWriteArrayList<>();

    private long startNanos = 0L;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, event -> {
            startNanos = System.nanoTime();
        });

        publisher.registerHandlerFor(TestCaseFinished.class, event -> {
            total.incrementAndGet();

            TestCase testCase = event.getTestCase();
            String scenarioName = testCase.getName();
            UUID id = testCase.getId();
            String uri = testCase.getUri() != null ? testCase.getUri().toString() : "unknown";
            int line = testCase.getLocation() != null ? testCase.getLocation().getLine() : -1;
            String errorMessage =event.getResult().getError().getMessage();

            Status status = event.getResult().getStatus();
            switch (status) {
                case PASSED:
                    pass.incrementAndGet();
                    break;
                case FAILED:
                    failed.incrementAndGet();
                    failedTestCases.add(String.format("%s (%s)  %s:%d %s", scenarioName, id, uri, line, System.lineSeparator() + errorMessage));
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
                    "Tests run: {}, Pass: {}, Failures: {}, Skipped: {}, Time elapsed: {} s",
                    total.get(),
                    total.get() - failed.get() - skipped.get(),
                    failed.get(),
                    skipped.get(),
                    String.format("%.3f", elapsedSeconds));
            if (failedTestCases.isEmpty()) {
                LOGGER.info("Failed test cases: None");
            } else {
                LOGGER.error("Failed test cases ({}):", failedTestCases.size());
                for (String failedTestCase : failedTestCases) {
                    LOGGER.error(" - {}", failedTestCase);
                }
            }
            // loggerUtils.newLine();
            LOGGER.info("=".repeat(70));
        });
    }
}
