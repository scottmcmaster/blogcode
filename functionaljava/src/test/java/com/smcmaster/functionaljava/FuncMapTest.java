package com.smcmaster.functionaljava;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link FuncMap} class.
 * 
 * @author smcmaster
 */
public class FuncMapTest {

  private FuncMap<String, String> map;
  
  @Before
  public void setUp() {
    map = new FuncMap<>(new HashMap<String, String>());
  }
  
  @Test
  public void testGetOptionNone() {
    Optional<String> result = map.getOption("user");
    assertFalse(result.isPresent());
  }

  @Test
  public void testGetOptionSome() {
    map.put("user", "scott");
    Optional<String> result = map.getOption("user");
    assertTrue(result.isPresent());
    assertEquals("scott", result.get());
  }

  @Test
  public void testNestedMapsFirstGetIsNull() {
    FuncMap<String, FuncMap<String, String>> nestedMap =
        new FuncMap<>(new HashMap<String, FuncMap<String, String>>());
    
    String result = nestedMap.getOption("user")
        .flatMap(x -> x.getOption("scott"))
        .orElse("UNKNOWN");
    assertEquals("UNKNOWN", result);
  }

  @Test
  public void testNestedMapsSecondGetIsNull() {
    FuncMap<String, FuncMap<String, String>> nestedMap =
        new FuncMap<>(new HashMap<String, FuncMap<String, String>>());
    nestedMap.put("user", new FuncMap<>(new HashMap<>()));
    
    String result = nestedMap.getOption("user")
        .flatMap(x -> x.getOption("scott"))
        .orElse("UNKNOWN");
    assertEquals("UNKNOWN", result);
  }

  @Test
  public void testNestedMapsWholeExpressionNotNull() {
    FuncMap<String, FuncMap<String, String>> nestedMap =
        new FuncMap<>(new HashMap<String, FuncMap<String, String>>());
    nestedMap.put("user", new FuncMap<>(new HashMap<>()));
    nestedMap.get("user").put("scott", "admin");
    
    String result = nestedMap.getOption("user")
        .flatMap(x -> x.getOption("scott"))
        .orElse("UNKNOWN");
    assertEquals("admin", result);
  }
}
