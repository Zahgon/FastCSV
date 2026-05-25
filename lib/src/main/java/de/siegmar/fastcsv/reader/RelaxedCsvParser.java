package de.siegmar.fastcsv.reader;

import static de.siegmar.fastcsv.util.Util.CR;
import static de.siegmar.fastcsv.util.Util.LF;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import de.siegmar.fastcsv.util.Nullable;
import de.siegmar.fastcsv.util.Preconditions;
import de.siegmar.fastcsv.util.Util;

/// Less strict but also less performant CSV parser.
@SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:ExecutableStatementCount", "checkstyle:InnerAssignment", "checkstyle:JavaNCSS", "checkstyle:NestedIfDepth" })
final class RelaxedCsvParser implements CsvParser {

    private static final char SPACE = ' ';

    private static final int EOF = -1;

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private final char fsep;

    @Nullable
    private final char[] fsepRemainder;

    private final char qChar;

    private final CommentStrategy cStrat;

    private final char cChar;

    private final boolean trimWhitespacesAroundQuotes;

    private final boolean allowUnclosedQuote;

    private final CsvCallbackHandler<?> callbackHandler;

    private final int maxBufferSize;

    private final LookaheadReader reader;

    private long startingLineNumber;

    private char[] currentField;

    private int currentFieldIndex;

    private int lines = 1;

    @SuppressWarnings("checkstyle:ParameterNumber")
    RelaxedCsvParser(final String fsep, final char qChar, final CommentStrategy cStrat, final char cChar, final boolean trimWhitespacesAroundQuotes, final boolean allowUnclosedQuote, final CsvCallbackHandler<?> callbackHandler, final int maxBufferSize, final Reader reader) {
        assertFields(fsep, qChar, cChar, cStrat);
        this.fsep = fsep.charAt(0);
        fsepRemainder = extractFsepRemainder(fsep);
        this.qChar = qChar;
        this.cStrat = cStrat;
        this.cChar = cChar;
        this.trimWhitespacesAroundQuotes = trimWhitespacesAroundQuotes;
        this.allowUnclosedQuote = allowUnclosedQuote;
        this.callbackHandler = callbackHandler;
        this.maxBufferSize = maxBufferSize;
        final int initialBufferSize = Math.min(maxBufferSize, DEFAULT_BUFFER_SIZE);
        this.reader = new LookaheadReader(reader, initialBufferSize, maxBufferSize);
        currentField = new char[initialBufferSize];
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    RelaxedCsvParser(final String fsep, final char qChar, final CommentStrategy cStrat, final char cChar, final boolean trimWhitespacesAroundQuotes, final boolean allowUnclosedQuote, final CsvCallbackHandler<?> callbackHandler, final int maxBufferSize, final String data) {
        assertFields(fsep, qChar, cChar, cStrat);
        this.fsep = fsep.charAt(0);
        fsepRemainder = extractFsepRemainder(fsep);
        this.qChar = qChar;
        this.cStrat = cStrat;
        this.cChar = cChar;
        this.trimWhitespacesAroundQuotes = trimWhitespacesAroundQuotes;
        this.allowUnclosedQuote = allowUnclosedQuote;
        this.callbackHandler = callbackHandler;
        this.maxBufferSize = maxBufferSize;
        final int dataSize = Math.max(data.length(), 1);
        reader = new LookaheadReader(new StringReader(data), dataSize, dataSize);
        currentField = new char[Math.min(maxBufferSize, data.length())];
    }

    private static void assertFields(final String fieldSeparator, final char quoteCharacter, final char commentCharacter, final CommentStrategy commentStrategy) {
        Preconditions.checkArgument(!Util.containsNewline(fieldSeparator), "fieldSeparator must not contain newline chars");
        Preconditions.checkArgument(!Util.isNewline(quoteCharacter), "quoteCharacter must not be a newline char");
        Preconditions.checkArgument(!Util.isNewline(commentCharacter), "commentCharacter must not be a newline char");
        if (commentStrategy == CommentStrategy.NONE) {
            Preconditions.checkArgument(!Util.containsDupe(fieldSeparator.charAt(0), quoteCharacter), "Control characters must differ (fieldSeparator=%s, quoteCharacter=%s)".formatted(fieldSeparator.charAt(0), quoteCharacter));
        } else {
            Preconditions.checkArgument(!Util.containsDupe(fieldSeparator.charAt(0), quoteCharacter, commentCharacter), "Control characters must differ (fieldSeparator=%s, quoteCharacter=%s, commentCharacter=%s)".formatted(fieldSeparator.charAt(0), quoteCharacter, commentCharacter));
        }
    }

    @Nullable
    @SuppressWarnings({ "PMD.AvoidLiteralsInIfCondition", "PMD.ReturnEmptyCollectionRatherThanNull" })
    private static char[] extractFsepRemainder(final String fsep) {
        if (fsep.length() <= 1) {
            return null;
        }
        final char[] fsepRemainder = new char[fsep.length() - 1];
        fsep.getChars(1, fsep.length(), fsepRemainder, 0);
        return fsepRemainder;
    }

    @SuppressWarnings({ "checkstyle:ReturnCount", "checkstyle:NPathComplexity" })
    @Override
    public boolean parse() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings({ "checkstyle:ReturnCount", "checkstyle:FinalParameters", "checkstyle:ParameterAssignment", "checkstyle:NPathComplexity", "checkstyle:BooleanExpressionComplexity", "PMD.AvoidReassigningParameters" })
    private boolean parseUnquoted(int ch) throws IOException {
        boolean endOfRecord = true;
        boolean onlyWhitespace = true;
        do {
            // fast-forward
            while (currentFieldIndex < currentField.length && reader.len > reader.start && ch != CR && ch != LF && ch != fsep && ch != qChar) {
                currentField[currentFieldIndex++] = (char) ch;
                if (onlyWhitespace && ch > SPACE) {
                    onlyWhitespace = false;
                }
                ch = reader.buffer[reader.start++];
            }
            if (ch == fsep && (fsepRemainder == null || reader.consumeIf(fsepRemainder))) {
                endOfRecord = false;
                break;
            }
            if (ch == CR) {
                reader.consumeLF();
                break;
            }
            if (ch == LF) {
                break;
            }
            if (ch == qChar && trimWhitespacesAroundQuotes && onlyWhitespace) {
                currentFieldIndex = 0;
                return parseQuoted();
            }
            appendChar(ch);
            if (onlyWhitespace && ch > SPACE) {
                onlyWhitespace = false;
            }
        } while ((ch = reader.read()) != EOF);
        callbackHandler.addField(currentField, 0, currentFieldIndex, false);
        currentFieldIndex = 0;
        return endOfRecord;
    }

    @SuppressWarnings({ "checkstyle:NPathComplexity", "checkstyle:ReturnCount", "checkstyle:BooleanExpressionComplexity", "PMD.AssignmentInOperand" })
    private boolean parseQuoted() throws IOException {
        boolean endOfRecord = true;
        int ch;
        OUTER: while (true) {
            ch = reader.read();
            if (ch == EOF) {
                if (!allowUnclosedQuote) {
                    throw new CsvParseException("Unclosed quoted field at end of input (record starting at line %d)".formatted(startingLineNumber));
                }
                break;
            }
            // fast-forward
            while (currentFieldIndex < currentField.length && reader.len > reader.start && ch != CR && ch != LF && ch != qChar) {
                currentField[currentFieldIndex++] = (char) ch;
                ch = reader.buffer[reader.start++];
            }
            if (ch == qChar && (ch = reader.read()) != qChar) {
                // closing quote
                for (; ch != EOF; ch = reader.read()) {
                    if (ch == CR) {
                        // CR right after closing quote
                        reader.consumeLF();
                        break OUTER;
                    }
                    if (ch == LF) {
                        // LF right after closing quote
                        break OUTER;
                    }
                    if (ch == fsep && (fsepRemainder == null || reader.consumeIf(fsepRemainder))) {
                        // field separator after closing quote
                        endOfRecord = false;
                        break OUTER;
                    }
                    if (!trimWhitespacesAroundQuotes || ch > SPACE) {
                        throw new CsvParseException("Unexpected character after closing quote: '%c' (0x%x)".formatted(ch, ch));
                    }
                }
                break;
            }
            appendChar(ch);
            if (ch == CR) {
                if (reader.consumeLF()) {
                    appendChar(LF);
                }
                lines++;
            } else if (ch == LF) {
                lines++;
            }
        }
        callbackHandler.addField(currentField, 0, currentFieldIndex, true);
        currentFieldIndex = 0;
        return endOfRecord;
    }

    @SuppressWarnings("PMD.AssignmentInOperand")
    private void parseComment() throws IOException {
        int ch;
        while ((ch = reader.read()) != EOF && ch != LF) {
            if (ch == CR) {
                reader.consumeLF();
                break;
            }
            appendChar(ch);
        }
        callbackHandler.setComment(currentField, 0, currentFieldIndex);
        currentFieldIndex = 0;
    }

    private void appendChar(final int ch) {
        if (currentField.length == currentFieldIndex) {
            if (currentField.length == maxBufferSize) {
                throw new CsvParseException("""
                    The maximum buffer size of %d is \
                    insufficient to read the data of a single field. \
                    This issue typically arises when a quotation begins but does not conclude within the \
                    confines of this buffer's maximum limit. \
                    """.formatted(maxBufferSize));
            }
            final char[] newField = new char[Math.min(maxBufferSize, currentField.length * 2)];
            System.arraycopy(currentField, 0, newField, 0, currentField.length);
            currentField = newField;
        }
        currentField[currentFieldIndex++] = (char) ch;
    }

    @Override
    public String peekLine() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("checkstyle:MultipleVariableDeclarations")
    @Override
    public void skipLine(final int numCharsToSkip) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getStartingLineNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("checkstyle:HiddenField")
    @Override
    public void reset(final long startingLineNumber) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final class LookaheadReader implements Closeable {

        private final Reader reader;

        private final int maxBufferSize;

        private char[] buffer;

        private int start;

        private int len;

        LookaheadReader(final Reader reader, final int bufferSize, final int maxBufferSize) {
            this.reader = reader;
            this.maxBufferSize = maxBufferSize;
            buffer = new char[bufferSize];
        }

        int read() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean consumeLF() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @SuppressWarnings("PMD.UseVarargs")
        boolean consumeIf(final char[] chars) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        String peekLine() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /// Ensures `required` characters are available in the buffer.
        /// Returns `true` if so, `false` if EOF was reached before that count was met.
        private boolean ensureBuffered(final int required) throws IOException {
            if (len == -1) {
                return false;
            }
            final int available = len - start;
            if (required <= available) {
                return true;
            }
            if (required > buffer.length) {
                // grow (relocates `start` to 0 along the way)
                if (required > maxBufferSize) {
                    throw new CsvParseException("The maximum buffer size of %d is insufficient to read a single line.".formatted(maxBufferSize));
                }
                final char[] newBuf = new char[Math.min(maxBufferSize, Math.max(buffer.length * 2, required))];
                System.arraycopy(buffer, start, newBuf, 0, available);
                buffer = newBuf;
                start = 0;
                len = available;
            } else if (required > buffer.length - start) {
                // compact
                System.arraycopy(buffer, start, buffer, 0, available);
                start = 0;
                len = available;
            }
            // fetch more data
            while (len - start < required) {
                final int count = reader.read(buffer, len, buffer.length - len);
                if (count == -1) {
                    if (start >= len) {
                        len = -1;
                    }
                    break;
                }
                len += count;
            }
            return len - start >= required;
        }

        void skip(final int numCharsToSkip) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void close() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
