package de.siegmar.fastcsv.writer;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/// High-performance buffered writer (without synchronization).
class FastBufferedWriter extends FilterWriter implements Writable {

    private final char[] buf;

    private int pos;

    FastBufferedWriter(final Writer writer, final int bufferSize) {
        super(writer);
        buf = new char[bufferSize];
    }

    @Override
    public void write(final int c) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void write(final char[] cbuf, final int off, final int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void write(final String str, final int off, final int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void endRecord() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void flushBuffer() throws IOException {
        out.write(buf, 0, pos);
        pos = 0;
    }

    @Override
    public void flush() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
