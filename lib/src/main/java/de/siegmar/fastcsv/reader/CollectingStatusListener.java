package de.siegmar.fastcsv.reader;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/// Implementation of [StatusListener] that collects updates.
///
/// This class is thread-safe.
public class CollectingStatusListener implements StatusListener {

    private final AtomicLong fileSize = new AtomicLong();

    private final AtomicLong recordCount = new AtomicLong();

    private final AtomicLong byteCount = new AtomicLong();

    private final AtomicBoolean completionStatus = new AtomicBoolean();

    private final AtomicReference<Throwable> failedThrowable = new AtomicReference<>();

    /// Default constructor.
    @SuppressWarnings("PMD.UnnecessaryConstructor")
    public CollectingStatusListener() {
    }

    @SuppressWarnings("checkstyle:HiddenField")
    @Override
    public void onInit(final long fileSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Get the total size in bytes.
    ///
    /// @return the total size in bytes
    public long getFileSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onReadRecord() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Get the number of records already indexed.
    ///
    /// @return the number of records already indexed
    public long getRecordCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onReadBytes(final int bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Get the number of bytes already read.
    ///
    /// @return the number of bytes already read
    public long getByteCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onComplete() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Get the completion status.
    ///
    /// @return `true`, when all data have been indexed successfully
    public boolean isCompleted() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onError(final Throwable throwable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Get the throwable that occurred while indexing.
    ///
    /// @return the throwable that occurred while indexing.
    public Optional<Throwable> getThrowable() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
