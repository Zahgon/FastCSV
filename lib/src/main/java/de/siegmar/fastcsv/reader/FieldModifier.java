package de.siegmar.fastcsv.reader;

import java.util.Objects;
import java.util.function.Function;

/// Implementations of this class are used within [CsvCallbackHandler] implementations to modify the fields of
/// a CSV record before storing them in the resulting object.
///
/// @see FieldModifiers
public interface FieldModifier {

    /// Builds a modifier that modifies the field value using the provided function.
    /// Comments are not modified.
    ///
    /// @param function the function to modify the field value. The contract of
    ///                 [FieldModifier#modify(long, int, boolean, String)] applies:
    ///                 the value passed to the function is never `null` and the return value must not be `null`.
    /// @return a new field modifier that applies the function to the field value
    /// @throws NullPointerException if the function is `null`
    static FieldModifier modify(final Function<? super String, String> function) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Gets called for every single field (that is not a comment).
    /// The Default implementation returns the field as is.
    ///
    /// @param startingLineNumber the starting line number (starting with 1)
    /// @param fieldIdx           the field index (starting with 0)
    /// @param quoted             `true` if the field was enclosed by the defined quote characters
    /// @param field              the field value, never `null`
    /// @return the modified field value (must not be `null`)
    default String modify(final long startingLineNumber, final int fieldIdx, final boolean quoted, final String field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Gets called for every comment.
    /// The Default implementation returns the field as is.
    ///
    /// @param startingLineNumber the starting line number (starting with 1)
    /// @param field              the field value (comment), never `null`
    /// @return the modified field value (must not be `null`)
    default String modifyComment(final long startingLineNumber, final String field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Chains multiple modifiers.
    ///
    /// @param after the next modifier to use.
    /// @return a composed field modifier that first applies this modifier and then applies the after modifier
    default FieldModifier andThen(final FieldModifier after) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
