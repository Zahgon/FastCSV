package de.siegmar.fastcsv.util;

/// Internal utility class.
///
/// It is **not** a part of the API!
public final class Util {

    /// Carriage return.
    public static final char CR = '\r';

    /// Line feed.
    public static final char LF = '\n';

    private Util() {
    }

    /// Checks if the given array of characters contains any duplicate characters.
    ///
    /// @param chars the array of characters to check for duplicates
    /// @return `true` if any character appears more than once in the array, `false` otherwise
    public static boolean containsDupe(final char... chars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Checks if the given character is a newline character.
    ///
    /// @param character character to test.
    /// @return `true` if the argument is [#CR] or [#LF]
    public static boolean isNewline(final char character) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Checks if the given string contains any newline characters.
    ///
    /// @param str the string to check for newlines
    /// @return `true` if the string contains either [#CR] or [#LF], `false` otherwise
    public static boolean containsNewline(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
