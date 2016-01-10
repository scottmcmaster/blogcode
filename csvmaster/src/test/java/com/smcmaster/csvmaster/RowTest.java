package com.smcmaster.csvmaster;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Row} class.
 * 
 * @author smcmaster
 */
public class RowTest {

  private static final String NAME = "Scott M";
  private static final String ADDRESS = "101 Main Street";
  private static final String ZIP = "90210";
  
  private List<String> headers;
  private List<String> values;
  
  @Before
  public void setUp() {
    headers = new ArrayList<>();
    headers.add("Name");
    headers.add("Address");
    headers.add("Zip");
    
    Map<String, Integer> headerMap = new HashMap<>();
    for (String header : headers) {
      headerMap.put(header, 0);
    }
    
    values = new ArrayList<>();
    values.add(NAME);
    values.add(ADDRESS);
    values.add(ZIP);
  }
  
  @Test
  public void testLoadFromNormal() {
    Row row = Row.loadFrom(headers, values);
    assertEquals(3, row.getValues().size());
    assertEquals(NAME, row.get("Name"));
    assertEquals(ADDRESS, row.get("Address"));
    assertEquals(ZIP, row.get("Zip"));
  }

  @Test
  public void testLoadFromTooManyValues() {
    values.add("Another one");
    Row row = Row.loadFrom(headers, values);
    assertEquals(3, row.getValues().size());
  }

  @Test
  public void testCreateEmpty() {
    Row row = new Row(headers);
    assertEquals(3, row.getValues().size());
    for (String value : row.getValues().values()) {
      assertNull(value);
    }
  }
  
  @Test
  public void testSetExisting() {
    Row row = Row.loadFrom(headers, values);
    assertTrue(row.set("Name", "Bob S"));
    assertEquals("Bob S", row.get("Name"));
  }

  @Test
  public void testSetNotExisting() {
    Row row = Row.loadFrom(headers, values);
    assertFalse(row.set("blah", "Bob S"));
    assertNull(row.get("blah"));
  }
}
