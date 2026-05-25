package de.siegmar.fastcsv.reader;

import java.util.Objects;
import java.util.function.Consumer;

/// A [CsvCallbackHandler] implementation that returns a [CsvRecord] for each record.
///
/// Example:
/// ```
/// CsvRecordHandler handler = CsvRecordHandler.builder()
///     .fieldModifier(FieldModifiers.TRIM)
///     .build();
/// ```
///
/// This implementation is stateful and must not be reused.
public final class CsvRecordHandler extends AbstractInternalCsvCallbackHandler<CsvRecord> {

    private CsvRecordHandler(final int maxFields, final int maxFieldSize, final int maxRecordSize, final FieldModifier fieldModifier) {
        super(maxFields, maxFieldSize, maxRecordSize, fieldModifier);
    }

    /// Constructs a new builder instance for this class.
    ///
    /// @return the builder
    /// @see #of(Consumer)
    public static CsvRecordHandlerBuilder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Constructs a new instance of this class with default settings.
    ///
    /// @return the new instance
    /// @see CsvRecordHandlerBuilder#build()
    public static CsvRecordHandler of() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Constructs a new instance of this class with the given configuration.
    ///
    /// This is an alternative to the builder pattern for convenience.
    ///
    /// @param configurer the configuration, must not be `null`
    /// @return the new instance
    /// @throws NullPointerException if `null` is passed
    /// @throws IllegalArgumentException if argument constraints are violated
    /// @see #builder()
    public static CsvRecordHandler of(final Consumer<CsvRecordHandlerBuilder> configurer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected CsvRecord buildRecord() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// A builder for [CsvRecordHandler].
    public static final class CsvRecordHandlerBuilder extends AbstractInternalCsvCallbackHandlerBuilder<CsvRecordHandlerBuilder> {

        private CsvRecordHandlerBuilder() {
        }

        @Override
        protected CsvRecordHandlerBuilder self() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /// Builds the [CsvRecordHandler] instance.
        ///
        /// @return the new instance
        /// @throws IllegalArgumentException if argument constraints are violated
        ///     (see [AbstractInternalCsvCallbackHandler])
        public CsvRecordHandler build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
