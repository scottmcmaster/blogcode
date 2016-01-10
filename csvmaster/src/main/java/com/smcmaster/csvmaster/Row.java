package com.smcmaster.csvmaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads a single row from an Apache Commons CSVRecord and exposes
 * the values keyed by column header.
 * 
 * @author smcmaster
 */
public class Row {

  private Map<String, String> values = new HashMap<>();
  
  /**
   * Loads a new Row from the given headers and values. If there are more
   * values than headers, they are ignored.
   */
  public static Row loadFrom(List<String> headers, Iterable<String> values) {
    Row row = new Row(headers);
    int headerIdx = 0;
    for (String value : values) {
      row.values.put(headers.get(headerIdx), value);
      if (++headerIdx >= headers.size()) {
        break;
      }
    }
    return row;
  }
  
  /**
   * Creates a new row with the columns (values) initialized
   * from the given header array.
   */
  public Row(List<String> headers) {
    for (String header : headers) {
      values.put(header, null);
    }
  }

  public Map<String, String> getValues() {
    return values;
  }
  
  public String get(String header) {
    return values.get(header);
  }
  
  /**
   * Sets a value in the row and returns true if the header is value.
   * If there is no corresponding header, returns false.
   */
  public boolean set(String header, String newValue) {
    if (values.containsKey(header)) {
      values.put(header, newValue);
      return true;
    }
    return false;
  }
}
