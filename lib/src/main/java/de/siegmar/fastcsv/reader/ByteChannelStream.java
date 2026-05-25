package de.siegmar.fastcsv.reader;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

final class ByteChannelStream {

    private final ByteBuffer byteBuf = ByteBuffer.allocateDirect(8192);

    private final ReadableByteChannel channel;

    private final CsvScanner.CsvListener csvListener;

    private long offset = -1;

    private int nextByte;

    // Keep one buf as Buffer to maintain Android compatibility
    // otherwise calls to clear() and flip() cause NoSuchMethodError
    // see https://www.morling.dev/blog/bytebuffer-and-the-dreaded-nosuchmethoderror/
    private final Buffer buf = byteBuf;

    ByteChannelStream(final ReadableByteChannel channel, final CsvScanner.CsvListener csvListener) throws IOException {
        this.channel = channel;
        this.csvListener = csvListener;
        nextByte = loadData() ? (char) byteBuf.get() : -1;
    }

    int get() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean consumeIfNextEq(final int val) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean hasData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    long getOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private int fetchNextByte() throws IOException {
        return buf.hasRemaining() || loadData() ? (char) byteBuf.get() : -1;
    }

    private boolean loadData() throws IOException {
        buf.clear();
        final int readCnt = channel.read(byteBuf);
        buf.flip();
        if (readCnt != -1) {
            csvListener.onReadBytes(readCnt);
            return true;
        }
        return false;
    }
}
