package org.example;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ReadListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DelegatingServletInputStream extends ServletInputStream {

    private final InputStream sourceStream;

    public DelegatingServletInputStream(String json) {
        this.sourceStream = new ByteArrayInputStream(json.getBytes());
    }

    @Override
    public int read() throws IOException {
        return sourceStream.read();
    }

    @Override
    public boolean isFinished() {
        try {
            return sourceStream.available() == 0;
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // метод не використовується
    }
}