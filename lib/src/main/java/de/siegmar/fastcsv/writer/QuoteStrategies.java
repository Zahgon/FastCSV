package de.siegmar.fastcsv.writer;

/// Provides some common [QuoteStrategy] implementations.
public enum QuoteStrategies implements QuoteStrategy {

    /// Enclose fields only if quoting is required.
    REQUIRED,
    /// Enclose any field with quotes regardless of its content (even empty and `null` fields).
    ALWAYS {

        @Override
        public boolean quoteNull(final int lineNo, final int fieldIdx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean quoteEmpty(final int lineNo, final int fieldIdx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean quoteValue(final int lineNo, final int fieldIdx, final String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    /// Enclose any field with quotes if it has content (is not empty or `null`).
    NON_EMPTY {

        @Override
        public boolean quoteValue(final int lineNo, final int fieldIdx, final String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    /// Enclose empty but not `null` fields to differentiate them.
    /// This is required for PostgreSQL CSV imports, for example.
    EMPTY {

        @Override
        public boolean quoteEmpty(final int lineNo, final int fieldIdx) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

}
