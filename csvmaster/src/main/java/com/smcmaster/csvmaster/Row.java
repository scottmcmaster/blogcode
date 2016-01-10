package com.smcmaster.csvmaster;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;

/**
 * Loads a single row from an Apache Commons CSVRecord and exposes
 * the values keyed by column header.
 * 
 * @author smcmaster
 */
public class Row {

  private Map<String, String> values = new HashMap<>();
  
  public static Row loadFrom(Map<String, Integer> headerMap, CSVRecord record) {
    Row row = new Row();
    for (String header : headerMap.keySet()) {
      row.values.put(header, record.get(header));
    }
    return row;
  }
  
  public Map<String, String> getValues() {
    return values;
  }
  
  public String get(String header) {
    return values.get(header);
  }
  
  public void set(String header, String newValue) {
    values.put(header, newValue);
  }
}
