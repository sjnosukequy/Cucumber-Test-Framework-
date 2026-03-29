package org.example.cucumber.src.api;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.example.cucumber.utils.loggerUtils;

public class ScenarioLogOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
        loggerUtils.append(new String(new byte[]{(byte) b}, StandardCharsets.UTF_8));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        loggerUtils.append(new String(b, off, len, StandardCharsets.UTF_8));
    }
}