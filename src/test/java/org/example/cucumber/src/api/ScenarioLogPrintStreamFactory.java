package org.example.cucumber.src.api;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class ScenarioLogPrintStreamFactory {
    private ScenarioLogPrintStreamFactory() {
    }

    public static PrintStream create() {
        return new PrintStream(new ScenarioLogOutputStream(), true, StandardCharsets.UTF_8);
    }
}