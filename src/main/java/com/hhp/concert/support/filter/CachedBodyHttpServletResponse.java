package com.hhp.concert.support.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream cachedContent = new ByteArrayOutputStream();
    private final ServletOutputStream outputStream = new CachedBodyServletOutputStream(cachedContent);
    private final PrintWriter writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    public String getResponseBody() throws IOException {
        copyBodyToResponse();

        return cachedContent.toString(StandardCharsets.UTF_8);
    }

    private void copyBodyToResponse() throws IOException {
        ServletOutputStream responseOutputStream = super.getOutputStream();
        responseOutputStream.write(cachedContent.toByteArray());
        responseOutputStream.flush();
    }

    private static class CachedBodyServletOutputStream extends ServletOutputStream {

        private final ByteArrayOutputStream cachedContent;

        public CachedBodyServletOutputStream(ByteArrayOutputStream cachedContent) {
            this.cachedContent = cachedContent;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }

        @Override
        public void write(int b) throws IOException {
            cachedContent.write(b);
        }

    }

}
