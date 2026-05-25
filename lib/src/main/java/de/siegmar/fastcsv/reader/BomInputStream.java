package de.siegmar.fastcsv.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

final class BomInputStream extends InputStream {

    private final InputStream delegate;

    private final Charset charset;

    private final byte[] buffer = new byte[BomUtil.POTENTIAL_BOM_SIZE];

    private int bufferPos;

    private int bufferLen;

    BomInputStream(final InputStream inputStream, final Charset defaultCharset) throws IOException {
        delegate = inputStream;
        final int bufCnt = delegate.readNBytes(buffer, 0, BomUtil.POTENTIAL_BOM_SIZE);
        final Optional<BomHeader> optHeader = BomUtil.detectCharset(buffer);
        if (optHeader.isEmpty()) {
            bufferPos = 0;
            bufferLen = bufCnt;
            charset = defaultCharset;
        } else {
            final BomHeader bomHeader = optHeader.get();
            bufferPos = bomHeader.getLength();
            bufferLen = bufCnt;
            charset = bomHeader.getCharset();
        }
    }

    Charset getCharset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int read() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private int readBuffer(final byte[] b, final int off, final int len) {
        final int toCopy = Math.min(bufferLen - bufferPos, len);
        System.arraycopy(buffer, bufferPos, b, off, toCopy);
        bufferPos += toCopy;
        return toCopy;
    }
}
