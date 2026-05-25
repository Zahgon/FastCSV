package de.siegmar.fastcsv.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringJoiner;

/// Represents an immutable CSV record with named (and indexed) fields.
///
/// The field values are never `null`. Empty fields are represented as empty strings.
///
/// Instances of this class are immutable and safe for use by multiple threads.
///
/// @see CsvReader
@SuppressWarnings("PMD.ArrayIsStoredDirectly")
public final class NamedCsvRecord extends CsvRecord {

    private final String[] header;

    @SuppressWarnings("PMD.UseVarargs")
    NamedCsvRecord(final long startingLineNumber, final String[] fields, final boolean comment, final String[] header) {
        super(startingLineNumber, fields, comment);
        this.header = header;
    }

    /// Retrieves the header names of this record.
    ///
    /// The header names are returned in the order they appear in the CSV file.
    ///
    /// Note that the header names are not necessarily unique.
    /// If you need to collect all fields with the same name (duplicate header), use [#getFieldsAsMapList()].
    ///
    /// Note that records for commented lines ([#isComment()]) do not have an empty header.
    /// To retrieve the comment value, use [#getField(int)] with index 0.
    ///
    /// @return the header names, never `null`
    public List<String> getHeader() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Retrieves the value of a field by its case-sensitive name, considering the first occurrence in case of
    /// duplicates.
    ///
    /// This method is equivalent to `findField(name).orElseThrow(NoSuchElementException::new)` although a
    /// more explanatory exception message is provided.
    ///
    /// @param name case-sensitive name of the field to be retrieved
    /// @return field value, never `null`
    /// @throws NoSuchElementException if this record has no such field
    /// @throws NullPointerException   if name is `null`
    /// @see #findField(String)
    /// @see #findFields(String)
    public String getField(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Finds the index for the first occurrence of the given header name (case-sensitive); returns -1 if not found
    private int findHeaderIndex(final String name) {
        for (int i = 0; i < header.length; i++) {
            if (name.equals(header[i])) {
                return i;
            }
        }
        return -1;
    }

    /// Retrieves the value of a field by its case-sensitive name, considering the first occurrence in case of
    /// duplicates.
    ///
    /// This method is equivalent to `findFields(name).stream().findFirst()` but more performant.
    ///
    /// @param name case-sensitive name of the field to be retrieved
    /// @return An [Optional] containing the value of the field if found,
    ///     or an [Optional#EMPTY] if the field is not present. Never returns `null`.
    /// @throws NullPointerException if name is `null`
    /// @see #findFields(String)
    public Optional<String> findField(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Collects all field values with the given name (case-sensitive) in the order they appear in the header.
    ///
    /// @param name case-sensitive name of the field to collect values for
    /// @return the field values (empty list if record doesn't contain that field), never `null`
    /// @throws NullPointerException if name is `null`
    public List<String> findFields(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Constructs an ordered map, associating header names with corresponding field values of this record,
    /// considering the first occurrence in case of duplicates.
    ///
    /// The constructed map will only contain entries for fields that have a key and a value. No map entry will have a
    /// `null` key or value.
    ///
    /// If you need to collect all fields with the same name (duplicate header), use [#getFieldsAsMapList()].
    ///
    /// @return an ordered map of header names and field values of this record, never `null`
    /// @see #getFieldsAsMapList()
    @SuppressWarnings("PMD.UseConcurrentHashMap")
    public Map<String, String> getFieldsAsMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /// Constructs an unordered map, associating header names with an ordered list of corresponding field values in
    /// this record.
    ///
    /// The constructed map will only contain entries for fields that have a key and a value. No map entry will have a
    /// `null` key or value.
    ///
    /// If you don't have to handle duplicate headers, you may simply use [#getFieldsAsMap()].
    ///
    /// @return an unordered map of header names and field values of this record, never `null`
    /// @see #getFieldsAsMap()
    @SuppressWarnings({ "PMD.AvoidInstantiatingObjectsInLoops", "PMD.UseConcurrentHashMap" })
    public Map<String, List<String>> getFieldsAsMapList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Mappings will only be created for fields that have a key and a value – return the minimum of both sizes
    private int commonSize() {
        return Math.min(header.length, fields.length);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
